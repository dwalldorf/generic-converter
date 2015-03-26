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
