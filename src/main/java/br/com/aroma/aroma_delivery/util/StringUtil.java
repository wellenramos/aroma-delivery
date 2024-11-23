package br.com.aroma.aroma_delivery.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

  public String capitalizeFirstLetter(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }
}
