package com.tal.abctime.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by irene on 2018/4/17.
 */

public class Utils {

    public final static String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public final static String VERIFY = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static boolean isPhoneNumber(String input) {
        return isMatchered(PHONE_PATTERN, input);
    }

    public static boolean isVerifyNumber(String input) {
        return input.length() == 4;
    }

    public static boolean isChinaPhoneLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isMatchered(String patternStr, String input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
