package com.androidlearning.whatsappclone.helpers;


import androidx.annotation.NonNull;

import io.socket.client.Socket;
import lombok.Getter;
import lombok.Setter;

public abstract class ESocket {

    @Getter
    @Setter
    private static Socket instance;


    public enum Emits {
        MESSAGE("MESSAGE");

        private final String value;

        Emits(String value) {
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            return value;
        }
    }

    public enum Events {
        NEW_CHAT("NEW_CHAT"),
        NEW_MESSAGE("NEW_MESSAGE");

        private final String value;

        Events(String value) {
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            return value;
        }
    }

}
