package sample.model;

/**
 * A paradigmatic model for a user. Its only use is to be converted for tests.
 */
public class User {

  private Long id;

  private String username;

  private String password;

  private String email;

  /**
   * This is a field that does not exist in the {@link sample.dto.UserDto}.<br />
   * It is present to make sure the converter doesn't fail in case this happens.
   */
  private String noMatchingFieldInDto;

  /**
   * This is a field name also exists in the {@link sample.dto.UserDto} but it is of type {@code Long}in the dto.<br />
   * It is present to make sure, the converter doesn't try to set this value to dto, because it makes sure, the
   * values to be converted, are of the same type.
   */
  private String matchingFieldIsOfDifferentType;

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

  public String getNoMatchingFieldInDto() {
    return noMatchingFieldInDto;
  }

  public void setNoMatchingFieldInDto(String noMatchingFieldInDto) {
    this.noMatchingFieldInDto = noMatchingFieldInDto;
  }
}
