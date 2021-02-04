package com.androidlearning.whatsappclone.services;

import com.androidlearning.whatsappclone.inputs.LoginWithEmailInput;
import com.androidlearning.whatsappclone.models.AuthTokenDTO;
import com.androidlearning.whatsappclone.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/email")
    Call<AuthTokenDTO> loginWithEmail(@Body LoginWithEmailInput input);

    @GET("auth")
    Call<UserModel> getAuthenticatedUser(@Header("Authorization") String token);


}
