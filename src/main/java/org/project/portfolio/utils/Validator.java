package org.project.portfolio.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
  private static final String EMAIL_REGEX =
          "^[a-zA-Z0-9_+&*-]+(?:\\." +
                  "[a-zA-Z0-9_+&*-]+)*@" +
                  "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                  "A-Z]{2,7}$";
  
  private static final String MOBILE_REGEX =
          "^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$";
  
  private static final String NAME_REGEX =
          "^[A-Za-z가-힣]{1,20}$";
  
  private static final String PASSWORD_REGEX =
          "(?=.*\\d{5,40})(?=.*[~`!@#$%\\^&*()-+=]{2,40})(?=.*[a-zA-Z]{5,40}).{12,40}$";
  
  public static boolean isValidEmail(String email) {
    Pattern pattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }
  
  public static boolean isValidMobile(String mobile) {
    Pattern pattern = Pattern.compile(MOBILE_REGEX);
    Matcher matcher = pattern.matcher(mobile);
    return matcher.matches();
  }
  
  public static boolean isValidName(String name) {
    Pattern pattern = Pattern.compile(NAME_REGEX);
    Matcher matcher = pattern.matcher(name);
    return matcher.matches();
  }
  
  public static boolean isValidPassword(String password) {
    Pattern pattern = Pattern.compile(PASSWORD_REGEX);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }
  
}
