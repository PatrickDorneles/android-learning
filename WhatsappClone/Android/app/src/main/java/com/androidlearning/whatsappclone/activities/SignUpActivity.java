package com.androidlearning.whatsappclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.androidlearning.whatsappclone.R;

import org.androidannotations.annotations.EActivity;

@EActivity
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}