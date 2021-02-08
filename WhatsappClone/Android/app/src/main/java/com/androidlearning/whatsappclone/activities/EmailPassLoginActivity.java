package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.config.AppConstants;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.ErrorBody;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;
import com.androidlearning.whatsappclone.inputs.LoginWithEmailInput;
import com.androidlearning.whatsappclone.models.AuthResponseModel;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.services.AuthService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import lombok.SneakyThrows;
import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

@EActivity
@SuppressLint("NonConstantResourceId")
public class EmailPassLoginActivity extends AppCompatActivity {

    @ViewById(R.id.email_layout)
    protected TextInputLayout mEmailLayout;

    @ViewById(R.id.email_edit)
    protected TextInputEditText mEmailEdit;

    @ViewById(R.id.pass_layout)
    protected TextInputLayout mPassLayout;

    @ViewById(R.id.pass_edit)
    protected TextInputEditText mPassEdit;

    private AuthService authService;
    private AuthTokenPreferences authTokenPreferences;
    private UserPreferences userPreferences;

    @SneakyThrows
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_pass_login);

        authService = RetrofitServiceFactory.create(AuthService.class);

        authTokenPreferences = new AuthTokenPreferences(this);
        userPreferences = new UserPreferences(this);

        verifyToken();
    }

    @Click(R.id.sign_in_button)
    protected void onClickSignIn() {

        val email = mEmailEdit.getText().toString();
        val password = mPassEdit.getText().toString();

        val loginInput = LoginWithEmailInput.builder()
                .email(email)
                .password(password)
                .build();

        signIn(loginInput);
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

    @TextChange(R.id.pass_edit)
    protected void onPassEditChange() { mPassLayout.setError(""); }

    @TextChange(R.id.email_edit)
    protected void onEmailEditChange() { mEmailLayout.setError(""); }

    protected void signIn(LoginWithEmailInput loginInput) {
        val call = authService.loginWithEmail(loginInput);

        call.enqueue(new Callback<AuthResponseModel>() {
            @SneakyThrows
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {
                clearErrors();

                if(!response.isSuccessful()) {
                    val error = ErrorBody.fromResponse(response);
                    mEmailLayout.setError(" ");
                    mPassLayout.setError(" ");
                    Toast.makeText(EmailPassLoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    return;
                }

                val token = response.body().getAuthToken();
                val user = response.body().getUser();

                userPreferences.saveUserPreferences(user);
                authTokenPreferences.saveAuthTokenPreferences("Bearer " + token);

                Toast.makeText(EmailPassLoginActivity.this, "Successfully signed in", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }

            @Override
            public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                Log.e("err", t.getMessage());
            }
        });
    }

    protected void verifyToken() {
        Log.i("verifyToken", "beginVerify");

        val token = authTokenPreferences.getToken();

        Log.i("token", token);

        if(token.isEmpty()) return;

        val call = authService.getAuthenticatedUser(token);

        call.enqueue(new Callback<UserModel>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()) {
                    userPreferences.saveUserPreferences(response.body());

                    goToMainActivity();
                    return;
                }

                Log.e("err", response.errorBody().string());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }

    protected void goToMainActivity() {
        val intent = new Intent(EmailPassLoginActivity.this, MainActivity_.class);
        startActivity(intent);
        finish();
    }

    protected void clearErrors() {
        mEmailLayout.setError("");
        mPassLayout.setError("");
    }
}