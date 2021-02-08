    package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.factories.RetrofitMainAPIFactory;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.ErrorBody;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.helpers.ValidationErrorBody;
import com.androidlearning.whatsappclone.inputs.UserSignUpByEmailInput;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.services.UserService;
import com.androidlearning.whatsappclone.utils.StringUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import lombok.SneakyThrows;
import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity
public class SignUpActivity extends AppCompatActivity {

    @ViewById(R.id.name_edit)
    protected TextInputEditText mNameEdit;

    @ViewById(R.id.email_edit)
    protected TextInputEditText mEmailEdit;

    @ViewById(R.id.pass_edit)
    protected TextInputEditText mPassEdit;

    @ViewById(R.id.name_layout)
    protected TextInputLayout mNameLayout;

    @ViewById(R.id.email_layout)
    protected TextInputLayout mEmailLayout;

    @ViewById(R.id.pass_layout)
    protected TextInputLayout mPassLayout;

    private UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userService = RetrofitServiceFactory.create(UserService.class);
    }

    @Click(R.id.sign_up_button)
    protected void onSignUpClicked() {

        val name = mNameEdit.getText().toString();
        val email = mEmailEdit.getText().toString();
        val password = mPassEdit.getText().toString();

        val signUpInput = UserSignUpByEmailInput.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        val call = userService.signUpUsingEmail(signUpInput);

        call.enqueue(new Callback<UserModel>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                clearErrors();

                if(!response.isSuccessful()) {
                    switch (response.code()) {
                        case 400:
                            val validationError = ValidationErrorBody.fromResponse(response);

                            for(val error: validationError.getMessage()) {
                                val errorMessage = StringUtils.capitalize(error);
                                if(error.toLowerCase().contains("name"))
                                    mNameLayout.setError(errorMessage);
                                if(error.toLowerCase().contains("email"))
                                    mEmailLayout.setError(errorMessage);
                                if(error.toLowerCase().contains("password"))
                                    mPassLayout.setError(errorMessage);
                            }

                            break;
                        case 403:
                            val errorBody = ErrorBody.fromResponse(response);
                            mEmailLayout.setError(errorBody.getMessage());
                            break;
                    }
                    return;
                }

                Toast.makeText(SignUpActivity.this, "Usuario cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                clearInputs();

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                //TODO

                Log.i("err", t.getMessage());
            }
        });

    }

    protected void clearErrors() {
        mNameLayout.setError("");
        mEmailLayout.setError("");
        mPassLayout.setError("");
    }

    protected void clearInputs() {
        mNameEdit.getText().clear();
        mEmailEdit.getText().clear();
        mPassEdit.getText().clear();
    }

}