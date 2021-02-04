package com.androidlearning.whatsappclone.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class GsonHelper {

    public static Gson GSON = new GsonBuilder()
            .setDateFormat("dd/MM/aa")
            .create();

    public static <T> T fromGson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static String toGson(Object src) {
        return GSON.toJson(src);
    }
}
