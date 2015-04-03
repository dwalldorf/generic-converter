package dwalldorf.jadecr;

import dwalldorf.jadecr.converter.Converter;
import dwalldorf.jadecr.converter.GetterSetterConverter;
import dwalldorf.jadecr.converter.PropertyConverter;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory to configure the converter and obtain an instance of it.
 */
public class ConverterFactory {

  /**
   * Map of converter instances by {@link dwalldorf.jadecr.ConverterType}
   */
  private final static Map<ConverterType, Converter> instanceMap = new HashMap<>();

  /**
   * The configured converter type
   */
  private static ConverterType converterType = ConverterType.GETTER_SETTER;

  /**
   * Returns a {@link dwalldorf.jadecr.converter.Converter} instance of the configured type.
   *
   * @return singleton instance of converter
   *
   * @see dwalldorf.jadecr.ConverterFactory#configureType(ConverterType)
   */
  public static Converter getInstance() {
    if (!instanceMap.containsKey(converterType)) {
      instanceMap.put(converterType, createInstance(converterType));
    }

    return instanceMap.get(converterType);
  }

  /**
   * Sets the {@code ConverterType}, this factory should return when the {@code getInstance} method is called.
   *
   * @param type the desired {@code ConverterType}
   */
  public static void configureType(final ConverterType type) {
    converterType = type;
  }

  /**
   * Creates a new instance of {@code type} and puts it into the {@code instanceMap}.
   *
   * @param type the converter type
   * @return a new converter instance of {@code type}
   */
  private static Converter createInstance(ConverterType type) {
    switch (type) {
      case GETTER_SETTER:
        return new GetterSetterConverter();
      case CLASS_MEMBER:
        return new PropertyConverter();
      default:
        return null;
    }
  }

}
