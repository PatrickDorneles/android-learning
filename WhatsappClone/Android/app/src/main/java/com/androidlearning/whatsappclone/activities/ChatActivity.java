package com.androidlearning.whatsappclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.adapters.ChatAdapter;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.ESocket;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.inputs.MessageRequestInput;
import com.androidlearning.whatsappclone.models.ChatResponseModel;
import com.androidlearning.whatsappclone.models.MessageResponseModel;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.services.ChatService;
import com.androidlearning.whatsappclone.utils.StringUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity
public class ChatActivity extends AppCompatActivity {

    @ViewById(R.id.top_bar)
    protected MaterialToolbar mTopBar;

    @ViewById(R.id.send)
    protected FloatingActionButton mSendButton;

    @ViewById(R.id.message)
    protected TextInputEditText mMessageInput;

    @ViewById(R.id.chat)
    protected ListView mChat;

    private ChatAdapter mChatAdapter;

    protected Socket mSocket;
    protected UserModel mContact;

    protected ChatResponseModel mChatInfo;
    protected String mToken;

    private AuthTokenPreferences authTokenPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        val chatService = RetrofitServiceFactory.create(ChatService.class);

        authTokenPreferences = AuthTokenPreferences.builder()
                .context(this)
                .build();

        mContact = GsonHelper.fromGson(
                getIntent().getStringExtra("contact"),
                UserModel.class
        );

        mToken = authTokenPreferences.getToken();

        val call = chatService.getChatWithUser(mContact.getId(), mToken);

        call.enqueue(new Callback<ChatResponseModel>() {
            @Override
            public void onResponse(Call<ChatResponseModel> call, Response<ChatResponseModel> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                mChatInfo = response.body();
                assert mChatInfo != null;
                mChatAdapter = new ChatAdapter(ChatActivity.this, mChatInfo);
                mChat.setAdapter(mChatAdapter);
                mChat.setSelection(mChat.getCount() - 1);
            }

            @Override
            public void onFailure(Call<ChatResponseModel> call, Throwable t) {

            }
        });

        setUpSocket();
        setUpScreen();
    }

    private final Emitter.Listener onReceiveNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            val newMessage = GsonHelper.fromGson(args[0].toString(), MessageResponseModel.class);

            if(mChatInfo != null && newMessage.getChatId().equals(mChatInfo.getId())) {
                mChatInfo.getMessages().add(newMessage);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatAdapter.notifyDataSetChanged();
                        mChat.setSelection(mChat.getCount() - 1);
                    }
                });
            }
        }
    };

    private final Emitter.Listener onReceiveNewChat = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            val newChatResponse = GsonHelper.fromGson(args[0].toString(), ChatResponseModel.class);

            if(newChatResponse.getContact().getId().equals(mContact.getId())) {
                mChatInfo = newChatResponse;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatAdapter = new ChatAdapter(ChatActivity.this, mChatInfo);
                        mChat.setAdapter(mChatAdapter);
                    }
                });

            }
        }
    };

    private void setUpSocket() {
        mSocket = ESocket.getInstance();

        mSocket.on(ESocket.Events.NEW_CHAT.toString(), onReceiveNewChat);
        mSocket.on(ESocket.Events.NEW_MESSAGE.toString(), onReceiveNewMessage);
    }

    private void setUpScreen() {
        val titleName = StringUtils.capitalize(mContact.getName());

        mTopBar.setTitle(titleName);
        if(mContact.getEmail() != null) {
            mTopBar.setSubtitle(mContact.getEmail());
        } else if(mContact.getPhoneNumber() != null) {
            mTopBar.setSubtitle(mContact.getPhoneNumber());
        }

        mTopBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val message = mMessageInput.getText().toString();

                if(message.trim().equals("")) {
                    Toast.makeText(
                            ChatActivity.this,
                            "Type a message to send",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                val messageRequest = MessageRequestInput.builder()
                        .text(message)
                        .receiverId(mContact.getId())
                        .token(mToken);

                mSocket.emit(
                        ESocket.Emits.MESSAGE.toString(),
                        GsonHelper.toGson(messageRequest)
                );

                mMessageInput.getText().clear();

            }
        });
    }

    @Override
    protected void onDestroy() {

        mSocket.off(ESocket.Events.NEW_CHAT.toString(), onReceiveNewChat);
        mSocket.off(ESocket.Events.NEW_MESSAGE.toString(), onReceiveNewMessage);

        super.onDestroy();
    }
}