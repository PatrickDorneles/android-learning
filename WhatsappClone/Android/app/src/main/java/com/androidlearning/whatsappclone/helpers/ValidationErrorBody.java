package com.androidlearning.whatsappclone.helpers;

import java.io.IOException;
import java.util.List;

import lombok.Getter;
import lombok.val;
import retrofit2.Response;

@Getter
public class ValidationErrorBody {

    int statusCode;
    List<String> message;
    String error;

    public static ValidationErrorBody fromResponse(Response<?> response) throws IOException {
        val errorBody = response.errorBody();
        assert errorBody != null;
        return GsonHelper.fromGson(errorBody.string(), ValidationErrorBody.class);
    }

}
