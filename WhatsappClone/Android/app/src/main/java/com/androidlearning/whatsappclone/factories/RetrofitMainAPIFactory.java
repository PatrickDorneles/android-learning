package com.androidlearning.whatsappclone.factories;

import com.androidlearning.whatsappclone.config.AppConstants;
import com.androidlearning.whatsappclone.helpers.GsonHelper;

import lombok.val;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitMainAPIFactory {

    public static Retrofit create() {
        val builder = new Retrofit.Builder();

        return builder
                .baseUrl(AppConstants.LOCAL_API_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.GSON))
                .build();
    }

}
