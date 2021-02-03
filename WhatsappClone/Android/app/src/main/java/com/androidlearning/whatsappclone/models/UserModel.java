package com.androidlearning.whatsappclone.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserModel {

    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private Boolean valid;

}
