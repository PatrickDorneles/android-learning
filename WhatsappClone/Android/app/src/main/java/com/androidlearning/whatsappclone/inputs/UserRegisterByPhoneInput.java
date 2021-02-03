package com.androidlearning.whatsappclone.inputs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public class UserRegisterInput {
    String name;
    String phoneNumber;
}
