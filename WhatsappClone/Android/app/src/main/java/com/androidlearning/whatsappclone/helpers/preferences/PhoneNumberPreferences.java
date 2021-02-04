package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;

import lombok.Builder;
import lombok.val;

public class PhoneNumberPreferences extends BasePreferences {

    private final String PHONE_NUMBER_KEY = "phoneNumber";

    @Builder
    public PhoneNumberPreferences(Context context) {
        super(context);
    }

    public void savePhoneNumberPreferences(String phoneNumber) {
        val editor = preferences.edit();

        editor.putString(PHONE_NUMBER_KEY, phoneNumber);
        editor.apply();
    }

    public void clearPhoneNumber() {
        val editor = preferences.edit();

        editor.remove(PHONE_NUMBER_KEY);
        editor.apply();
    }

    public String getPhoneNumber() {
        return preferences.getString(PHONE_NUMBER_KEY, "");
    }


}
