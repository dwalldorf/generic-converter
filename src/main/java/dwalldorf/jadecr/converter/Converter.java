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

/**
 * A converter for POJO-style java objects.<br>
 * It allows {@link dwalldorf.jadecr.Convertible} annotated objects to be converted.
 *
 * @see dwalldorf.jadecr.Convertible
 */
public interface Converter {

  /**
   * Converts {@code src} to an object of {@code destType}, specified by the {@link dwalldorf.jadecr.Convertible}
   * annotation.<br>
   * {@code src} must be annotated {@code Convertible}.
   *
   * @param src object to convert
   * @return the converted object
   *
   * @throws ConversionException in case of any error
   */
  public Object convert(Object src) throws ConversionException;

}
