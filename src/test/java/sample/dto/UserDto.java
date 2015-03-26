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
package sample.dto;

/**
 * A paradigmatic DTO for a user. Its only use is to be converted for tests.
 */
public class UserDto {

  private Long id;

  private String username;

  private String password;

  private String email;

  /**
   * This is a field name also exists in the {@link sample.model.User} but it is of type {@code String}in the entity.
   * <br />
   * It is present to make sure, the converter doesn't try to set this value to entity, because it makes sure, the
   * values to be converted, are of the same type.
   */
  private Long noMatchingFieldInDto;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getNoMatchingFieldInDto() {
    return noMatchingFieldInDto;
  }

  public void setNoMatchingFieldInDto(Long noMatchingFieldInDto) {
    this.noMatchingFieldInDto = noMatchingFieldInDto;
  }
}
