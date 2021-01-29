package com.androidlearning.whatsappclone.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    protected Context context;
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;

    private final String FILE_NAME = "whatsapp_preferences";
    private final int MODE = Context.MODE_PRIVATE;

    public Preferences(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(FILE_NAME, MODE);
        editor = preferences.edit();
    }

}
