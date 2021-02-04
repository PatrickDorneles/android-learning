package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;

import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.models.UserModel;

import lombok.val;

public class UserPreferences extends BasePreferences {


    private final String USER_KEY = "user";

    public UserPreferences(Context context) {
        super(context);
    }

    public void saveUserPreferences(UserModel userModel) {
        val jsonUser = GsonHelper.GSON.toJson(userModel);
        editor.putString(USER_KEY, jsonUser);
        editor.commit();
    }

    public UserModel getUserData() {
        val jsonUser = preferences.getString(USER_KEY, "");
        return GsonHelper.fromGson(jsonUser, UserModel.class);
    }

}
