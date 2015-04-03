package dwalldorf.jadecr;

import static org.junit.Assert.*;

import dwalldorf.jadecr.converter.Converter;
import dwalldorf.jadecr.converter.GetterSetterConverter;
import dwalldorf.jadecr.converter.PropertyConverter;
import org.junit.Test;

public class ConverterFactoryTest {

  @Test
  public void createsGetterSetterByDefault() {
    Converter converterInstance = ConverterFactory.getInstance();

    if (!(converterInstance instanceof GetterSetterConverter)) {
      fail("converter is no GetterSetterConverter");
    }
  }

  @Test
  public void createsGetterSetterIfConfigured() {
    ConverterFactory.configureType(ConverterType.GETTER_SETTER);
    Converter converterInstance = ConverterFactory.getInstance();

    if (!(converterInstance instanceof GetterSetterConverter)) {
      fail("converter factory is configured to GetterSetter but returned other");
    }
  }

  @Test
  public void createsPropertyConverterIfConfigured() {
    ConverterFactory.configureType(ConverterType.CLASS_MEMBER);
    Converter converterInstance = ConverterFactory.getInstance();

    if (!(converterInstance instanceof PropertyConverter)) {
      fail("converter factory is configured to ClassMember but returned other");
    }
  }

}