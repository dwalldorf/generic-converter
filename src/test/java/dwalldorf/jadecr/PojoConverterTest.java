package dwalldorf.jadecr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import sample.dto.UserDto;
import sample.model.User;

public class PojoConverterTest {

  @Test
  public void testConvertToDto() {
    User user = mockUser();
    UserDto dto = PojoConverter.convert(user, UserDto.class);

    assertNotNull(dto);
    assertEquals(user.getId(), dto.getId());
    assertEquals(user.getUsername(), dto.getUsername());
    assertEquals(user.getPassword(), dto.getPassword());
    assertEquals(user.getEmail(), dto.getEmail());
  }

  @Test
  public void testConvertToEntity() {
    UserDto userDto = mockUserDto();
    User entity = PojoConverter.convert(userDto, User.class);

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

  private UserDto mockUserDto() {
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    userDto.setUsername("testuser");
    userDto.setEmail("mail@host.tld");
    userDto.setPassword("password");

    return userDto;
  }
}