package com.androidlearning.whatsappclone.inputs;

import lombok.Builder;

@Builder
public class ValidatorInput {
    String validationToken;
    String phoneNumber;
}
