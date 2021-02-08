package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.adapters.SectionPagerAdapter;
import com.androidlearning.whatsappclone.config.AppConstants;
import com.androidlearning.whatsappclone.fragments.ChatListFragment;
import com.androidlearning.whatsappclone.fragments.ContactListFragment;
import com.androidlearning.whatsappclone.helpers.ESocket;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.PhoneNumberPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import lombok.SneakyThrows;
import lombok.val;

@EActivity
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.top_bar)
    protected MaterialToolbar mTopBar;

    @ViewById(R.id.tab_layout)
    protected TabLayout mTabLayout;

    @ViewById(R.id.view_pager)
    protected ViewPager mViewPager;

    private AuthTokenPreferences authTokenPreferences;
    private UserPreferences userPreferences;
    private PhoneNumberPreferences phoneNumberPreferences;

    private Socket mSocket;

    @SneakyThrows
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPreferences = UserPreferences.builder()
                .context(this)
                .build();
        phoneNumberPreferences = PhoneNumberPreferences.builder()
                .context(this)
                .build();
        authTokenPreferences = AuthTokenPreferences.builder()
                .context(this)
                .build();

        configureMenuOptions();
        setUpViewPager();
        setUpSocket();
    }

    private Emitter.Listener onReceiveNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    private Emitter.Listener onReceiveNewChat = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    protected void setUpSocket() throws URISyntaxException {

        val token = authTokenPreferences.getToken();

        URI uri = URI.create(AppConstants.LOCAL_SOCKET_URL);
        IO.Options options = IO.Options.builder()
                .setForceNew(true)
                .setUpgrade(false)
                .setTransports(new String[]{WebSocket.NAME})
                .setPort(AppConstants.LOCAL_SOCKET_PORT)
                .setExtraHeaders(
                        Collections.singletonMap(
                                "authorization",
                                Collections.singletonList(token)
                        )
                )
                .build();

        mSocket = IO.socket(uri, options);

        mSocket.on(ESocket.Events.NEW_MESSAGE.toString(), onReceiveNewMessage);
        mSocket.on(ESocket.Events.NEW_CHAT.toString(), onReceiveNewChat);

        mSocket.connect();

        ESocket.setInstance(mSocket);
    }

    protected void setUpViewPager() {
        val viewPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(ChatListFragment.newInstance(this), "CHATS");
        viewPagerAdapter.addFragment(ContactListFragment.newInstance(this), "CONTACTS");

        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    protected void configureMenuOptions() {
        mTopBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.menu_sign_out:
                        signOut();
                        return true;
                    case R.id.search:
                        // DO OTHER STUFF
                        return true;
                    default:
                        return false;
                }
            }
        });
    }



    protected void signOut() {
        authTokenPreferences.clearToken();
        userPreferences.clearUser();
        phoneNumberPreferences.clearPhoneNumber();

        Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show();

        val intent = new Intent(this, EmailPassLoginActivity_.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {

        mSocket.off();
        mSocket.disconnect();

        super.onDestroy();
    }
}