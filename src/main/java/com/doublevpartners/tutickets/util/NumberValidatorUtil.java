package com.doublevpartners.tutickets.util;

public class NumberValidatorUtil {

  private NumberValidatorUtil () {
  }

  public static boolean isNumber(String number) {
    try {
      Integer.parseInt(number);
    } catch (NumberFormatException e){
      return false;
    }

    return true;
  }
}
