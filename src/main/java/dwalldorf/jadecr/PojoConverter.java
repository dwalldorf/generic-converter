/*
 jadecR
 Copyright (C) 2015  Daniel Walldorf

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package dwalldorf.jadecr;

import dwalldorf.jadecr.exception.ConversionException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

/**
 *
 */
public class PojoConverter {

  public Object convert(Object src) throws ConversionException {
    Object dest = null;

    if (!isConvertibleObject(src)) {
      return null;
    }

    if (src != null) {
      try {
        dest = getNewDestInstance(src);
        copyValues(src, dest);
      } catch (Exception e) {
        throw new ConversionException(e.getMessage(), e);
      }
    }

    return dest;
  }

  /**
   * Tells whether {@code object} is annotated with {@link dwalldorf.jadecr.Convertible} or not.
   *
   * @param object to check
   * @return boolean
   */
  private boolean isConvertibleObject(final Object object) {
    if (object == null) {
      return false;
    }
    return object.getClass().isAnnotationPresent(Convertible.class);
  }

  private Object getNewDestInstance(final Object src) throws Exception {
    return getConvertibleDestClass(src).newInstance();
  }

  private Class getConvertibleDestClass(final Object object) {
    Convertible annotation = object.getClass().getAnnotation(Convertible.class);
    return annotation.destClass();
  }

  private void copyValues(final Object src, Object dest) throws Exception {
    Method[] methods = src.getClass().getMethods();
    for (Method method : methods) {
      if (!isGetter(method)) {
        continue;
      }

      Object value = getValue(method, src);

      if (isConvertibleObject(value)) {
        Optional<Method> optionalSetter = getSetter(method, dest, getConvertibleDestClass(value));
        if (!optionalSetter.isPresent()) {
          continue;
        }

        Method setter = optionalSetter.get();
        Object convertedValue = convert(value);
        setValue(convertedValue, setter, dest);
      } else {
        Optional<Method> optionalSetter = getSetter(method, dest);
        if (!optionalSetter.isPresent()) {
          continue;
        }

        Method setter = optionalSetter.get();
        setValue(value, setter, dest);
      }
    }
  }

  /**
   * Tells whether {@code method} is a (publicly accessible) getter or not.
   *
   * @param method the method to check
   * @return boolean, telling you if this method is a getter
   */
  private boolean isGetter(final Method method) {
    // check if method starts with 'get'
    if (!method.getName().startsWith("get")) {
      return false;
    }

    // check if method takes more than 0 arguments
    if (method.getTypeParameters().length > 0) {
      return false;
    }

    // check if method is accessible
    if (method.getModifiers() == Modifier.PRIVATE) {
      return false;
    }
    return true;
  }

  private Optional<Method> getSetter(final Method getter, final Object dest, final Class valueType) {
    String setterName = "set" + getter.getName().substring(3);

    for (Method method : dest.getClass().getMethods()) {
      if (!isSetter(method)) {
        continue;
      }
      if (!method.getName().equals(setterName)) {
        continue;
      }

      Class<?> setterParameterType = method.getParameterTypes()[0];

      if (!setterParameterType.getName().equals(valueType.getName())) {
        continue;
      }

      return Optional.of(method);
    }

    return Optional.empty();
  }

  private Optional<Method> getSetter(final Method getter, final Object dest) {
    Class<?> getterReturnType = getter.getReturnType();
    return getSetter(getter, dest, getterReturnType);
  }

  private boolean isSetter(final Method method) {
    return method.getName().startsWith("set");
  }

  private Object getValue(final Method getter, final Object src) throws Exception {
    return getter.invoke(src);
  }

  private void setValue(final Object value, final Method setter, Object dest) throws Exception {
    setter.invoke(dest, value);
  }

}
