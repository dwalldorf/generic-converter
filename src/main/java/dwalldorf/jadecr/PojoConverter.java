/**
 * jadecR
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
import java.util.Optional;

/**
 *
 */
public class PojoConverter {

  public static <D> D convert(Object src, final Class<D> destClass) throws ConversionException {
    D dest = null;

    if (src != null) {
      try {
        dest = destClass.newInstance();
        copyValues(src, dest);
      } catch (Exception e) {
        throw new ConversionException(e.getMessage(), e);
      }
    }

    return dest;
  }

  private static void copyValues(Object src, Object dest) throws Exception {
    Method[] methods = src.getClass().getMethods();
    for (Method method : methods) {
      if (!isGetter(method)) {
        continue;
      }

      Optional<Method> optionalSetter = getSetter(method, dest);
      if (!optionalSetter.isPresent()) {
        continue;
      }

      Method setter = optionalSetter.get();
      Object value = getValue(method, src);
      setValue(value, setter, dest);
    }
  }

  private static boolean isGetter(Method method) {
    return method.getName().startsWith("get");
  }

  private static Optional<Method> getSetter(Method getter, Object dest) {
    String setterName = "set" + getter.getName().substring(3);

    for (Method method : dest.getClass().getMethods()) {
      if (!isSetter(method)) {
        continue;
      }
      if (!method.getName().equals(setterName)) {
        continue;
      }

      Class<?> setterParameterType = method.getParameterTypes()[0];
      Class<?> getterReturnType = getter.getReturnType();
      if (!setterParameterType.getName().equals(getterReturnType.getName())) {
        continue;
      }

      return Optional.of(method);
    }

    return Optional.empty();
  }

  private static boolean isSetter(Method method) {
    return method.getName().startsWith("set");
  }

  private static Object getValue(Method getter, Object src) throws Exception {
    return getter.invoke(src);
  }

  private static void setValue(Object value, Method setter, Object dest) throws Exception {
    setter.invoke(dest, value);
  }

}
