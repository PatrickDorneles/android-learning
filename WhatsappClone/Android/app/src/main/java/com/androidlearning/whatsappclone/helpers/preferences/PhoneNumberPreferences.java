package com.androidlearning.whatsappclone.helpers.preferences;

import android.content.Context;

public class PhoneNumberPreferences extends BasePreferences {

    private final String PHONE_NUMBER_KEY = "phoneNumber";

    public PhoneNumberPreferences(Context context) {
        super(context);
    }

    public void savePhoneNumberPreferences(String phoneNumber) {
        editor.putString(PHONE_NUMBER_KEY, phoneNumber);
        editor.commit();
    }

    public String getPhoneNumber() {
        return preferences.getString(PHONE_NUMBER_KEY, "");
    }


}
