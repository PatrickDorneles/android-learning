package com.androidlearning.whatsappclone.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.activities.ChatActivity;
import com.androidlearning.whatsappclone.activities.ChatActivity_;
import com.androidlearning.whatsappclone.adapters.ChatAdapter;
import com.androidlearning.whatsappclone.adapters.ChatListAdapter;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.ESocket;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.models.ChatResponseModel;
import com.androidlearning.whatsappclone.models.MessageResponseModel;
import com.androidlearning.whatsappclone.services.ChatService;

import java.util.List;
import java.util.function.Predicate;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.NonNull;
import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatListFragment extends Fragment {

    @NonNull
    private Context activityContext;

    private List<ChatResponseModel> mChats;
    private ListView mChatsList;
    private ChatListAdapter mChatListAdapter;

    private Socket mSocket;

    public ChatListFragment() {
    }

    public static ChatListFragment newInstance(Context activityContext) {
        ChatListFragment fragment = new ChatListFragment();
        fragment.activityContext = activityContext;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        val view = inflater.inflate(R.layout.fragment_chats, container, false);

        val authTokenPreferences = AuthTokenPreferences.builder()
                .context(activityContext)
                .build();

        val chatService = RetrofitServiceFactory.create(ChatService.class);

        mChatsList = (ListView) view.findViewById(R.id.chats_list);

        val token = authTokenPreferences.getToken();

        val call = chatService.getChatsFromAuthedUser(token);

        call.enqueue(new Callback<List<ChatResponseModel>>() {
            @Override
            public void onResponse(Call<List<ChatResponseModel>> call, Response<List<ChatResponseModel>> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                mChats = response.body();
                mChatListAdapter = new ChatListAdapter(activityContext, mChats);
                mChatsList.setAdapter(mChatListAdapter);
            }

            @Override
            public void onFailure(Call<List<ChatResponseModel>> call, Throwable t) {

            }
        });

        mChatsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                val chat = mChats.get(position);
                val contactGson = GsonHelper.toGson(chat.getContact());

                val intent = new Intent(activityContext, ChatActivity_.class);
                intent.putExtra("contact", contactGson);
                startActivity(intent);
            }
        });

        setUpSocket();

        return view;
    }

    private final Emitter.Listener onReceiveNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            val newMessage = GsonHelper.fromGson(args[0].toString(), MessageResponseModel.class);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    val optionalChat = mChats.stream()
                            .filter(c -> c.getId().equals(newMessage.getChatId()))
                            .findFirst();

                    if(optionalChat.isPresent()) {
                        val chat = optionalChat.get();
                        chat.getMessages().add(newMessage);
                        mChatListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };

    private final Emitter.Listener onReceiveNewChat = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            val newChatResponse = GsonHelper.fromGson(args[0].toString(), ChatResponseModel.class);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChats.add(newChatResponse);
                    mChatListAdapter.notifyDataSetChanged();
                }
            });

        }
    };

    private void setUpSocket() {
        mSocket = ESocket.getInstance();

        mSocket.on(ESocket.Events.NEW_CHAT.toString(), onReceiveNewChat);
        mSocket.on(ESocket.Events.NEW_MESSAGE.toString(), onReceiveNewMessage);
    }

    @Override
    public void onDestroy() {

        mSocket.off(ESocket.Events.NEW_CHAT.toString(), onReceiveNewChat);
        mSocket.off(ESocket.Events.NEW_MESSAGE.toString(), onReceiveNewMessage);

        super.onDestroy();
    }

}