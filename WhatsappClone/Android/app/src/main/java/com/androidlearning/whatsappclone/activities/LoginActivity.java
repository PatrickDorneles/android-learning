package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.helpers.Preferences;
import com.androidlearning.whatsappclone.helpers.UserPreferences;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

@EActivity
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.main_view)
    protected LinearLayout mMainView;

    @ViewById(R.id.phone_number_edit)
    protected TextInputEditText mPhoneNumberEdit;

    @ViewById(R.id.country_code_edit)
    protected TextInputEditText mCountryCodeEdit;

    @ViewById(R.id.name_edit)
    protected TextInputEditText mName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(mPhoneNumberEdit, simpleMaskFormatter);
        mPhoneNumberEdit.addTextChangedListener(maskTextWatcher);

    }

    @Click(R.id.sign_up_button)
    protected void onSignUp() {
        String name = mName.getText().toString();
        String phoneNumber = mPhoneNumberEdit.getText().toString();
        String rawPhoneNumber = phoneNumber.replaceAll( "[^\\d]", "");

        String fullPhoneNumber =
                mCountryCodeEdit.getText().toString()
                + rawPhoneNumber;

        Random randomInstance = new Random();
        int randomNumber = randomInstance.nextInt( 999999 - 100000) + 100000;

        String token = String.valueOf(randomNumber);

        Snackbar.make(mMainView, token, Snackbar.LENGTH_LONG);

        UserPreferences preferences = new UserPreferences(this);
        preferences.saveUserPreferences(name, phoneNumber, token);

    }

}