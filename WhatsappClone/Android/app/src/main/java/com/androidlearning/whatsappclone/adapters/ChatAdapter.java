package com.androidlearning.whatsappclone.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;
import com.androidlearning.whatsappclone.models.ChatResponseModel;
import com.androidlearning.whatsappclone.models.MessageResponseModel;

import lombok.Setter;
import lombok.val;

public class ChatAdapter extends ArrayAdapter<MessageResponseModel> {

    private Context context;

    @Setter
    private ChatResponseModel chatInfo;

    private ChatResponseModel chat;

    public ChatAdapter(@NonNull Context context, @NonNull ChatResponseModel chat) {
        super(context, -1, chat.getMessages());
        this.context = context;
        this.chat = chat;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        val userPreferences = UserPreferences.builder()
                .context(context)
                .build();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        val user = userPreferences.getUserData();

        val message = getItem(position);

        val userIsUser = message.getUser().getId().equals(user.getId());

        if(userIsUser) {
            convertView = inflater.inflate(R.layout.listitem_chat_user_message, parent, false);
        } else {
            convertView = inflater.inflate(R.layout.listitem_chat_contact_message, parent, false);
        }

        val messageView = (TextView) convertView.findViewById(R.id.message_text);



        messageView.setText(message.getText());

        return convertView;

    }
}
