package dwalldorf.jadecr;

import dwalldorf.jadecr.converter.Converter;
import dwalldorf.jadecr.converter.GetterSetterConverter;
import dwalldorf.jadecr.converter.PropertyConverter;

/**
 * Factory to configure the converter and obtain an instance of it.
 */
public class ConverterFactory {

  private static Converter instance = null;

  private static ConverterType converterType = ConverterType.GETTER_SETTER;

  public static Converter getInstance() {
    if (instance == null) {
      switch (converterType) {
        case GETTER_SETTER:
          instance = new GetterSetterConverter();
          break;
        case CLASS_MEMBER:
          instance = new PropertyConverter();
          break;
      }
    }

    return instance;
  }

  public static void configureType(final ConverterType type) {
    converterType = type;
  }

}
