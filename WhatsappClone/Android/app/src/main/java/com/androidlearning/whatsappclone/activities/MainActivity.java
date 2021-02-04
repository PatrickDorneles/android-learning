package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.PhoneNumberPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import lombok.val;

@SuppressLint("NonConstantResourceId")
@EActivity
public class MainActivity extends AppCompatActivity {

    private AuthTokenPreferences authTokenPreferences;
    private UserPreferences userPreferences;
    private PhoneNumberPreferences phoneNumberPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authTokenPreferences = AuthTokenPreferences.builder()
                .context(this)
                .build();
        userPreferences = UserPreferences.builder()
                .context(this)
                .build();
        phoneNumberPreferences = PhoneNumberPreferences.builder()
                .context(this)
                .build();
    }

    @Click(R.id.sign_out_button)
    protected void onSignOut() {
        authTokenPreferences.clearToken();
        userPreferences.clearUser();
        phoneNumberPreferences.clearPhoneNumber();

        Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show();

        val intent = new Intent(this, EmailPassLoginActivity_.class);
        startActivity(intent);
        finish();
    }
}