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

  private final static Map<ConverterType, Converter> instanceMap = new HashMap<>();

  private static ConverterType converterType = ConverterType.GETTER_SETTER;

  public static Converter getInstance() {
    if (!instanceMap.containsKey(converterType)) {
      instanceMap.put(converterType, createInstance(converterType));
    }

    return instanceMap.get(converterType);
  }

  public static void configureType(final ConverterType type) {
    converterType = type;
  }

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
