package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.androidlearning.whatsappclone.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import lombok.val;

@EActivity
@SuppressLint("NonConstantResourceId")
public class EmailPassLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_pass_login);
    }

    @Click(R.id.to_login_link)
    protected void onClickToLogin() {
        val intent = LoginActivity_.intent(this).get();
        startActivity(intent);
        finish();
    }

    @Click(R.id.to_sign_up_link)
    protected void onClickToSignUp() {
        val intent = SignUpActivity_.intent(this).get();
        startActivity(intent);
    }
}