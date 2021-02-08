package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.inputs.UserRegisterByPhoneInput;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.services.UserService;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import lombok.val;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

@EActivity
@SuppressLint("NonConstantResourceId")
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.main_view)
    protected LinearLayout mMainView;

    @ViewById(R.id.phone_number_edit)
    protected TextInputEditText mPhoneNumberEdit;

    @ViewById(R.id.country_code_edit)
    protected TextInputEditText mCountryCodeEdit;

    @ViewById(R.id.name_edit)
    protected TextInputEditText mName;

    private UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userService = RetrofitServiceFactory.create(UserService.class);

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(mPhoneNumberEdit, simpleMaskFormatter);
        mPhoneNumberEdit.addTextChangedListener(maskTextWatcher);

    }

    @Click(R.id.sign_up_button)
    protected void onSignUp() {
        val name = mName.getText().toString();
        val phoneNumber = mPhoneNumberEdit.getText().toString();
        val rawPhoneNumber = phoneNumber.replaceAll( "[^\\d]", "");

        val fullPhoneNumber =
                mCountryCodeEdit.getText().toString()
                + rawPhoneNumber;

        val registerInput = UserRegisterByPhoneInput.builder()
                .name(name)
                .phoneNumber(fullPhoneNumber)
                .build();

        val call = userService.sendRegisterRequest(registerInput);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.i("here", "here");
                if(response.isSuccessful()) {
                    val user = response.body();
                    val userJson = GsonHelper.GSON.toJson(user);

                    Log.i("user", userJson);

                    val intent = new Intent(LoginActivity.this, ValidationActivity_.class);
                    intent.putExtra(ValidationActivity.USER_KEY_EXTRA, userJson);
                    intent.putExtra(ValidationActivity.PHONE_KEY_EXTRA, fullPhoneNumber);
                    startActivity(intent);
                    finish();

                    return;
                }

                Log.i("res", response.toString());
                Toast.makeText(LoginActivity.this, "SMS sending failed, try again later", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

    }

    @Click(R.id.to_login_email_pass_link)
    protected void onClickToLoginEmailPass() {
        val intent = EmailPassLoginActivity_.intent(this).get();
        startActivity(intent);
        finish();
    }

}