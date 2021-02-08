package com.androidlearning.whatsappclone.factories;

import com.androidlearning.whatsappclone.config.AppConstants;
import com.androidlearning.whatsappclone.helpers.GsonHelper;

import java.io.IOException;

import lombok.val;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public abstract class RetrofitMainAPIFactory {

    public static Retrofit create() {
        val builder = new Retrofit.Builder();

        return builder
                .baseUrl(AppConstants.LOCAL_API_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.GSON))
                .build();
    }

}
