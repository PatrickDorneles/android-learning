package com.androidlearning.whatsappclone.utils;

public abstract class StringUtils {

    public static String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

}
