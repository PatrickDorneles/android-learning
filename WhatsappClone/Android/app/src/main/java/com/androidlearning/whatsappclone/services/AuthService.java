package com.androidlearning.whatsappclone.services;

import com.androidlearning.whatsappclone.inputs.LoginWithEmailInput;
import com.androidlearning.whatsappclone.models.AuthResponseModel;
import com.androidlearning.whatsappclone.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("auth/email")
    Call<AuthResponseModel> loginWithEmail(@Body LoginWithEmailInput input);

    @Headers("Content-Type: application/json")
    @GET("auth")
    Call<UserModel> getAuthenticatedUser(@Header("Authorization") String token);


}
