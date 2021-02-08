package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.ErrorBody;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;
import com.androidlearning.whatsappclone.inputs.ValidatorInput;
import com.androidlearning.whatsappclone.models.AuthResponseModel;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.services.UserService;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import lombok.SneakyThrows;
import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity
@SuppressLint("NonConstantResourceId")
public class ValidationActivity extends AppCompatActivity {

    public static final String USER_KEY_EXTRA = "user";
    public static final String PHONE_KEY_EXTRA = "phone";

    @ViewById(R.id.validation_code_edit)
    protected TextInputEditText mValidationCodeEdit;

    private UserModel mUser;
    private String mPhoneNumber;

    private UserService userService;
    private UserPreferences userPreferences;
    private AuthTokenPreferences authTokenPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        userPreferences = new UserPreferences(this);
        authTokenPreferences = new AuthTokenPreferences(this);

        val userExtra = getIntent().getStringExtra(USER_KEY_EXTRA);
        mUser = GsonHelper.fromGson(userExtra, UserModel.class);
        mPhoneNumber = getIntent().getStringExtra(PHONE_KEY_EXTRA);

        userService = RetrofitServiceFactory.create(UserService.class);

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNNNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(mValidationCodeEdit, simpleMaskFormatter);
        mValidationCodeEdit.addTextChangedListener(maskTextWatcher);
    }


    @Click(R.id.validate_button)
    protected void onValidate() {

        val validationToken = mValidationCodeEdit.getText().toString();

        Log.i("userId", mUser.getId());

        val input = ValidatorInput.builder()
                .validationToken(validationToken)
                .phoneNumber(mPhoneNumber)
                .build();

        val call = userService.validatePhoneNumber(input, mUser.getId());

        call.enqueue(new Callback<AuthResponseModel>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {

                if(!response.isSuccessful()) {
                    val error = ErrorBody.fromResponse(response);
                    Toast.makeText(ValidationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                val token = response.body().getAuthToken();
                val user = response.body().getUser();

                userPreferences.saveUserPreferences(user);
                authTokenPreferences.saveAuthTokenPreferences("Bearer " + token);

                Toast.makeText(ValidationActivity.this, "Successfully signed in", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }

            @Override
            public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                Log.e("err", t.getMessage());
            }
        });
    }

    protected void goToMainActivity() {
        val intent = new Intent(ValidationActivity.this, MainActivity_.class);
        startActivity(intent);
        finish();
    }

}