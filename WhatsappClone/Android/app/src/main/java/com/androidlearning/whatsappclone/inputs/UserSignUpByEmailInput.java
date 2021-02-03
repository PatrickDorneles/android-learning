package com.androidlearning.whatsappclone.inputs;

import lombok.Builder;

@Builder
public class UserSignUpByEmailInput {
    String name;
    String email;
    String password;
}
