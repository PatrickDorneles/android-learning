package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.phone_number_edit)
    protected TextInputEditText mPhoneNumberEdit;

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
        String nome = mName.getText().toString();
        String

    }

}