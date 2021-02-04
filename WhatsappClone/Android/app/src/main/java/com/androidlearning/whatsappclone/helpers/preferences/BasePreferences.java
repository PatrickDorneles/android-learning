package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class BasePreferences {

    protected Context context;
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;

    private final String FILE_NAME = "com.androidlearning.whatsappclone.preferences";
    private final int MODE = Context.MODE_PRIVATE;

    public BasePreferences(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(FILE_NAME, MODE);
        editor = preferences.edit();
    }

}
