package com.property.campaign.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

  private static final String EMAIL_REGEX =
      "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  public static boolean isValid(String email) {
    Matcher matcher = EMAIL_PATTERN.matcher(email);
    return matcher.matches();
  }

}

