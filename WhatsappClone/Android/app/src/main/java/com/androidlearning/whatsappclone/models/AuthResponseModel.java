package com.androidlearning.whatsappclone.models;

import lombok.Getter;

@Getter
public class AuthResponseModel {
    String authToken;
    UserModel user;
}
