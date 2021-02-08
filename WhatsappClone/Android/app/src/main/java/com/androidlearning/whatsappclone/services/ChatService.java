package com.androidlearning.whatsappclone.services;

import com.androidlearning.whatsappclone.models.ChatResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ChatService {

    @GET("chat/with-user/{id}")
    Call<ChatResponseModel> getChatWithUser(@Path("id") String id, @Header("Authorization") String token);

    @GET("chat")
    Call<List<ChatResponseModel>> getChatsFromAuthedUser(@Header("Authorization") String token);

}
