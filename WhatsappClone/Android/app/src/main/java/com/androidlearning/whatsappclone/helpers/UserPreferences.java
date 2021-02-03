package com.androidlearning.whatsappclone.helpers;

import android.content.Context;
import android.widget.Toast;

import com.androidlearning.whatsappclone.models.UserModel;

import java.util.HashMap;

import lombok.val;

public class UserPreferences extends Preferences {


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

        return GsonHelper.GSON.fromJson(jsonUser, UserModel.class);
    }

}
