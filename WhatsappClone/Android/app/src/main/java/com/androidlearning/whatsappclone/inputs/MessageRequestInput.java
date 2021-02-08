package com.androidlearning.whatsappclone.inputs;

import lombok.Builder;

@Builder
public class MessageRequestInput {
    String token;
    String receiverId;
    String text;
}

