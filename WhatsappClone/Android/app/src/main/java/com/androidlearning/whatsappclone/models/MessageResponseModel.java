package com.androidlearning.whatsappclone.models;

import java.util.Date;

import lombok.Getter;

@Getter
public class MessageResponseModel {
    String text;
    UserModel user;
    String chatId;
    Date dateTime;
}
