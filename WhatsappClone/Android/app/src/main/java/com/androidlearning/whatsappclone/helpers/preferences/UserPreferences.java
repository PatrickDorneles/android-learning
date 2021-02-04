package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;

import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.models.UserModel;

import lombok.Builder;
import lombok.val;

public class UserPreferences extends BasePreferences {


    private final String USER_KEY = "user";

    @Builder
    public UserPreferences(Context context) {
        super(context);
    }

    public void saveUserPreferences(UserModel userModel) {
        val editor = preferences.edit();

        val jsonUser = GsonHelper.GSON.toJson(userModel);
        editor.putString(USER_KEY, jsonUser);
        editor.apply();
    }

    public void clearUser() {
        val editor = preferences.edit();

        editor.remove(USER_KEY);
        editor.apply();
    }

    public UserModel getUserData() {
        val jsonUser = preferences.getString(USER_KEY, "");
        return GsonHelper.fromGson(jsonUser, UserModel.class);
    }

}
