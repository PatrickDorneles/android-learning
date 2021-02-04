package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;

import lombok.Builder;
import lombok.val;

public class AuthTokenPreferences extends BasePreferences {


    private final String TOKEN_KEY = "auth_token";

    @Builder
    public AuthTokenPreferences(Context context) { super(context); }

    public void saveAuthTokenPreferences(String token) {
        val editor = preferences.edit();

        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public void clearToken() {
        val editor = preferences.edit();

        editor.remove(TOKEN_KEY);
        editor.apply();
    }

    public String getToken() {
        return preferences.getString(TOKEN_KEY, "");
    }

}
