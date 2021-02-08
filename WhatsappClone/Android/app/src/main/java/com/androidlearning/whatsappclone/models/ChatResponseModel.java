package com.androidlearning.whatsappclone.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResponseModel {
    String id;
    UserModel contact;
    List<MessageResponseModel> messages;
}
