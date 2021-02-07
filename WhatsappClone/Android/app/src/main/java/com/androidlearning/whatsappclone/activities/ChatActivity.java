package com.androidlearning.whatsappclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.models.UserModel;
import com.androidlearning.whatsappclone.utils.StringUtils;
import com.google.android.material.appbar.MaterialToolbar;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import lombok.val;

@EActivity
public class ChatActivity extends AppCompatActivity {

    @ViewById(R.id.top_bar)
    protected MaterialToolbar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        val contact = GsonHelper.fromGson(
                getIntent().getStringExtra("contact"),
                UserModel.class
        );

        val titleName = StringUtils.capitalize(contact.getName());

        mTopBar.setTitle(titleName);
        mTopBar.setSubtitle(contact.getEmail());
        mTopBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}