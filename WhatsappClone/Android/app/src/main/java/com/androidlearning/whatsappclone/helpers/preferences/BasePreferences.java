package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import lombok.AccessLevel;
import lombok.Builder;


public class BasePreferences {

    protected Context context;
    protected SharedPreferences preferences;

    private final String FILE_NAME = "com.androidlearning.whatsappclone.preferences";
    private final int MODE = Context.MODE_PRIVATE;

    public BasePreferences(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(FILE_NAME, MODE);
    }

}
