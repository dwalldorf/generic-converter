package dwalldorf.jadecr.exception;

/**
 * Exception to be thrown in case something goes wrong while converting an object.
 */
public class ConversionException extends RuntimeException {

  public ConversionException(String message, Throwable cause) {
    super(message, cause);
  }

}
