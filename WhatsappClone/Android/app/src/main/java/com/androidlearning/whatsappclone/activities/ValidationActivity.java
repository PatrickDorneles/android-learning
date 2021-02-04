package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;
import com.androidlearning.whatsappclone.models.UserModel;
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

    public static final String USER_KEY_EXTRA = "user";

    @ViewById(R.id.validation_code_edit)
    protected TextInputEditText mValidationCodeEdit;

    private UserModel mUser;

    private UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        val userExtra = getIntent().getStringExtra(USER_KEY_EXTRA);
        mUser = GsonHelper.fromGson(userExtra, UserModel.class);

        userService = RetrofitServiceFactory.create(UserService.class);

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