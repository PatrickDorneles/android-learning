package com.androidlearning.whatsappclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.utils.StringUtils;

import java.util.List;

import lombok.val;

public class ContactsAdapter extends ArrayAdapter<UserModel> {

    private Context context;

    public ContactsAdapter(@NonNull Context context, @NonNull List<UserModel> objects) {
        super(context, -1, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        UserModel contact = getItem(position);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_contact, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.contact_name);
        TextView identifierView = (TextView) convertView.findViewById(R.id.contact_identifier);

        val nameLabel = StringUtils.capitalize(contact.getName());

        nameView.setText(nameLabel);

        if(contact.getEmail() != null) {
            identifierView.setText(contact.getEmail());
        } else if(contact.getPhoneNumber() != null)  {
            identifierView.setText(contact.getPhoneNumber());
        } else {
            identifierView.setVisibility(View.GONE);
        }

        return convertView;
    }



}
