package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.factories.RetrofitMainAPIFactory;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.inputs.UserSignUpByEmailInput;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()) {
                    // TODO
                }
                //TODO
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                //TODO
            }
        });


    }
}