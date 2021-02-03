package com.androidlearning.whatsappclone.factories;

import lombok.val;

public abstract class RetrofitServiceFactory {

    public static <T> T create(final Class<T> service){
        val retrofit = RetrofitMainAPIFactory.create();
        return retrofit.create(service);
    }

}
