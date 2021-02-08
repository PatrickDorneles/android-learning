package com.androidlearning.whatsappclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.models.ChatResponseModel;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.utils.StringUtils;

import java.util.List;

import lombok.val;

public class ChatListAdapter extends ArrayAdapter<ChatResponseModel> {

    private Context context;

    public ChatListAdapter(@NonNull Context context, @NonNull List<ChatResponseModel> objects) {
        super(context, -1, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ChatResponseModel chat = getItem(position);

        val contact = chat.getContact();
        val messages = chat.getMessages();

        val lastMessage = messages.get(messages.size() - 1);
        val lastMessageLabel = lastMessage.getUser().getName() + ": " + lastMessage.getText();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_chat_instance, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.contact_name);
        TextView lastMessageView = (TextView) convertView.findViewById(R.id.last_message);

        val nameLabel = StringUtils.capitalize(contact.getName());

        nameView.setText(nameLabel);
        lastMessageView.setText(lastMessageLabel);

        return convertView;
    }


}
