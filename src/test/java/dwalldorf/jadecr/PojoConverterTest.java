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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import sample.dto.MessageDto;
import sample.dto.UserDto;
import sample.model.Message;
import sample.model.User;

public class PojoConverterTest {

  private PojoConverter pojoConverter = new PojoConverter();

  @Test
  public void testConvertToDto() {
    User user = mockUser();
    Message message = mockMessage(user);

    MessageDto messageDto = (MessageDto) pojoConverter.convert(message);
    UserDto userDto = messageDto.getUser();

    assertNotNull(messageDto);
    assertEquals(message.getId(), messageDto.getId());
    assertEquals(message.getTitle(), messageDto.getTitle());
    assertEquals(message.getMessage(), messageDto.getMessage());

    assertNotNull(userDto);
    assertEquals(user.getId(), userDto.getId());
    assertEquals(user.getUsername(), userDto.getUsername());
    assertEquals(user.getPassword(), userDto.getPassword());
    assertEquals(user.getEmail(), userDto.getEmail());
  }

  @Test
  public void testConvertToEntity() {
    UserDto userDto = mockUserDto();
    User entity = (User) pojoConverter.convert(userDto);

    assertNotNull(entity);
    assertEquals(userDto.getId(), entity.getId());
    assertEquals(userDto.getUsername(), entity.getUsername());
    assertEquals(userDto.getPassword(), entity.getPassword());
    assertEquals(userDto.getEmail(), entity.getEmail());
  }

  private User mockUser() {
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setEmail("mail@host.tld");
    user.setPassword("password");
    user.setNoMatchingFieldInDto("this field does not exist in dto");

    return user;
  }

  private Message mockMessage(final User user) {
    Message message = new Message();
    message.setId(42L);
    message.setTitle("title");
    message.setMessage("message");
    message.setUser(user);

    return message;
  }

  private UserDto mockUserDto() {
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    userDto.setUsername("testuser");
    userDto.setEmail("mail@host.tld");
    userDto.setPassword("password");

    return userDto;
  }
}