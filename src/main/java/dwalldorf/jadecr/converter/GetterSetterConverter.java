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
package dwalldorf.jadecr.converter;

import dwalldorf.jadecr.exception.ConversionException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

/**
 * This converter will search for all {@code getter}-methods in the src object and try to find a matching setter in the
 * desired {code destType} object.<br /> While converting, it will actually use the getters and setters of the
 * respecting objects.<br /><br />
 *
 * Objects to be converted, must have the {@link dwalldorf.jadecr.Convertible} annotation configured.
 *
 * @see GetterSetterConverter#isGetter(java.lang.reflect.Method)
 * @see GetterSetterConverter#getSetter(java.lang.reflect.Method, Object, Class)
 * @see GetterSetterConverter#copyValues(Object, Object)
 * @see dwalldorf.jadecr.Convertible
 */
public class GetterSetterConverter implements Converter {

  public Object convert(Object src) throws ConversionException {
    if (!ConvertUtil.isConvertibleObject(src)) {
      return null;
    }

    try {
      Object dest = ConvertUtil.getNewDestInstance(src);
      copyValues(src, dest);

      return dest;
    } catch (Exception e) {
      throw new ConversionException(e.getMessage(), e);
    }
  }

  private void copyValues(final Object src, Object dest) throws Exception {
    Method[] methods = src.getClass().getMethods();
    for (Method method : methods) {
      if (!isGetter(method)) {
        continue;
      }

      Object value = getValue(method, src);

      if (ConvertUtil.isConvertibleObject(value)) {
        Optional<Method> optionalSetter = getSetter(method, dest, ConvertUtil.getConvertibleDestClass(value));
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

  // @formatter:off
  /**
   * Tells whether {@code method} is a (publicly accessible) getter or not.<br />
   * The method must matching the following criteria:
   * <ul>
   *   <li>its name starts with {@code get}</li>
   *   <li>it takes exactly 0 arguments</li>
   *   <li>its modifier is not {@code private}</li>
   * </ul>
   *
   * @param method the method to check
   *
   * @return boolean, telling you if this method is a getter
   */
  // @formatter:on
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

  // @formatter:off
  /**
   * This method tries to find a matching setter method in {@code dest} and return it.<br />
   * It works like this:
   * <ul>
   *   <li>
   *     define the matching setter name:
   *     <ul>
   *       <li>
   *         remove {@code get} from {@code getter.getName()}
   *       </li>
   *       <li>
   *         prepend {@code set}
   *       </li>
   *     </ul>
   *   </li>
   *   <li>
   *     find the setter method:
   *     <ul>
   *       <li>
   *         find all setters
   *       </li>
   *       <li>
   *         see if the setter method name matches the setter name
   *       </li>
   *       <li>
   *         see if the {@code valueType} and the argument type of the setter are equal
   *       </li>
   *     </ul>
   *   </li>
   * </ul>
   *
   * @param getter the getter method
   * @param dest the object, we need to find a setter method for
   * @param valueType the return type of the getter method
   *
   * @return the matching setter method or {@code Optional.empty()}
   */
  // @formatter:on
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

  /**
   * Gets the return type of the {@code getter} and calls {@link dwalldorf.jadecr.converter.GetterSetterConverter#getSetter(java.lang.reflect.Method,
   * Object, Class)}.
   *
   * @param getter the getter method
   * @param dest   the object, we need to find a setter method for
   *
   * @return the matching setter method or {@code Optional.empty()}
   *
   * @see dwalldorf.jadecr.converter.GetterSetterConverter#getSetter(java.lang.reflect.Method, Object, Class)
   */
  private Optional<Method> getSetter(final Method getter, final Object dest) {
    Class<?> getterReturnType = getter.getReturnType();
    return getSetter(getter, dest, getterReturnType);
  }

  // @formatter:off
  /**
   * Tells whether {@code method} is a (publicly accessible) setter or not.<br />
   * The method must matching the following criteria:
   * <ul>
   *   <li>its name starts with {@code set}</li>
   *   <li>it takes exactly 1 argument</li>
   *   <li>its modifier is not {@code private}</li>
   * </ul>
   *
   * @param method the method to check
   *
   * @return boolean, telling you if this method is a getter
   */
  // @formatter:on
  private boolean isSetter(final Method method) {
    // check if method starts with 'set'
    if (!method.getName().startsWith("set")) {
      return false;
    }

    // make sure method takes exactly 1 argument
    if (method.getTypeParameters().length == 1) {
      return false;
    }

    // check if method is accessible
    if (method.getModifiers() == Modifier.PRIVATE) {
      return false;
    }
    return true;
  }

  /**
   * Invokes the getter on {@code src}.
   *
   * @param getter the getter to invoke
   * @param src    object to apply getter
   *
   * @return the return value of the getter
   *
   * @throws Exception
   */
  private Object getValue(final Method getter, final Object src) throws Exception {
    return getter.invoke(src);
  }

  /**
   * Invokes the {@code setter} on with {@code value} on {@code dest}.
   *
   * @param value  the value to set
   * @param setter the setter method
   * @param dest   object to apply setter
   *
   * @throws Exception
   */
  private void setValue(final Object value, final Method setter, Object dest) throws Exception {
    setter.invoke(dest, value);
  }

}
