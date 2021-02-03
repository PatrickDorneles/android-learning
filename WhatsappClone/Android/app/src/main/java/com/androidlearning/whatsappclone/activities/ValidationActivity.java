package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.factories.RetrofitMainAPIFactory;
import com.androidlearning.whatsappclone.helpers.UserPreferences;
import com.androidlearning.whatsappclone.services.UserService;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import lombok.val;

@EActivity
@SuppressLint("NonConstantResourceId")
public class ValidationActivity extends AppCompatActivity {

    @ViewById(R.id.validation_code_edit)
    protected TextInputEditText mValidationCodeEdit;

    private UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        val retrofit = RetrofitMainAPIFactory.create();
        userService = retrofit.create(UserService.class);

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNNNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(mValidationCodeEdit, simpleMaskFormatter);
        mValidationCodeEdit.addTextChangedListener(maskTextWatcher);
    }


    @Click(R.id.validate_button)
    protected void onValidate() {
        val userPreferences = new UserPreferences(this);

        val user = userPreferences.getUserData();
    }

}