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

import dwalldorf.jadecr.Convertible;

/**
 *
 */
class ConvertUtil {

  static Object getNewDestInstance(final Object src) throws Exception {
    return getConvertibleDestClass(src).newInstance();
  }

  static Class getConvertibleDestClass(final Object object) {
    Convertible annotation = object.getClass().getAnnotation(Convertible.class);
    return annotation.destClass();
  }

  /**
   * Tells whether {@code object} is annotated with {@link dwalldorf.jadecr.Convertible} or not.
   *
   * @param object to check
   *
   * @return boolean
   */
  static boolean isConvertibleObject(final Object object) {
    if (object == null) {
      return false;
    }
    return object.getClass().isAnnotationPresent(Convertible.class);
  }

}
