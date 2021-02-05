package com.androidlearning.whatsappclone.services;

import com.androidlearning.whatsappclone.inputs.AddContactInput;
import com.androidlearning.whatsappclone.inputs.UserRegisterByPhoneInput;
import com.androidlearning.whatsappclone.inputs.UserSignUpByEmailInput;
import com.androidlearning.whatsappclone.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface UserService {

    @POST("user/sign-up/phone")
    Call<UserModel> sendRegisterRequest(@Body() UserRegisterByPhoneInput registerInput);

    @POST("user/sign-up/email")
    Call<UserModel> signUpUsingEmail(@Body() UserSignUpByEmailInput signUpByEmailInput);

    @POST("user/add/contact")
    Call<UserModel> addContact(@Body()AddContactInput addContactInput, @Header("Authorization") String token);

}
