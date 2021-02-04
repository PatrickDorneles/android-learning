package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;

public class AuthTokenPreferences extends BasePreferences {


    private final String TOKEN_KEY = "auth_token";

    public AuthTokenPreferences(Context context) { super(context); }

    public void saveAuthTokenPreferences(String token) {
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public void clearToken() {
        editor.remove(TOKEN_KEY);
    }

    public String getToken() {
        return preferences.getString(TOKEN_KEY, "");
    }

}
