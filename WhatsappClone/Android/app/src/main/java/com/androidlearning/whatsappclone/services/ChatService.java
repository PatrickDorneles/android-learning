package com.androidlearning.whatsappclone.services;

import com.androidlearning.whatsappclone.models.ChatResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ChatService {

    @Headers("Content-Type: application/json")
    @GET("chat/with-user/{id}")
    Call<ChatResponseModel> getChatWithUser(@Path("id") String id, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET("chat")
    Call<List<ChatResponseModel>> getChatsFromAuthedUser(@Header("Authorization") String token);

}
