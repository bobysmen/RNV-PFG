package com.example.rnv_pfg.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationsUtils {

    private ValidationsUtils(){

    }


    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }

}
