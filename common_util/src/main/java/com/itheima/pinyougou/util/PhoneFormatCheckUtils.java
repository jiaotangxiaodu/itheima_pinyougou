package com.itheima.pinyougou.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneFormatCheckUtils {

    public static boolean isPhoneLegal(String phone) {

        String regExp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

        Pattern p = Pattern.compile(regExp);

        Matcher m = p.matcher(phone);

        return m.find();//boolean
    }
}
