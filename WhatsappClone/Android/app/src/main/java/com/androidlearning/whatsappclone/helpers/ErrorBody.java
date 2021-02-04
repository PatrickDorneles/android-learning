package com.androidlearning.whatsappclone.helpers;

import java.io.IOException;

import lombok.Getter;
import lombok.val;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Getter
public class ErrorBody {
    int statusCode;
    String message;
    String error;

    public static ErrorBody fromResponse(Response<?> response) throws IOException {
        val errorBody = response.errorBody();
        return GsonHelper.fromGson(errorBody.string(), ErrorBody.class);
    }


}
