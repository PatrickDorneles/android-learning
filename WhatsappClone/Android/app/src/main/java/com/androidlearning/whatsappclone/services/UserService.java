package com.androidlearning.whatsappclone.services;

import com.androidlearning.whatsappclone.inputs.AddContactInput;
import com.androidlearning.whatsappclone.inputs.LoginWithEmailInput;
import com.androidlearning.whatsappclone.inputs.UserRegisterByPhoneInput;
import com.androidlearning.whatsappclone.inputs.UserSignUpByEmailInput;
import com.androidlearning.whatsappclone.inputs.ValidatorInput;
import com.androidlearning.whatsappclone.models.AuthResponseModel;
import com.androidlearning.whatsappclone.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("user/sign-up/phone")
    Call<UserModel> sendRegisterRequest(@Body() UserRegisterByPhoneInput registerInput);

    @Headers("Content-Type: application/json")
    @POST("user/sign-up/validate/{id}")
    Call<AuthResponseModel> validatePhoneNumber(@Body ValidatorInput input, @Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("user/sign-up/email")
    Call<UserModel> signUpUsingEmail(@Body() UserSignUpByEmailInput signUpByEmailInput);

    @Headers("Content-Type: application/json")
    @POST("user/add/contact")
    Call<UserModel> addContact(@Body() String emailOrPassword, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET("user/contacts")
    Call<List<UserModel>> getContacts(@Header("Authorization") String token);


}
