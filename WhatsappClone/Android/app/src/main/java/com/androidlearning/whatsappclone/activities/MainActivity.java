package com.androidlearning.whatsappclone.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.adapters.SectionPagerAdapter;
import com.androidlearning.whatsappclone.fragments.ChatsFragment;
import com.androidlearning.whatsappclone.fragments.ContactsFragment;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.PhoneNumberPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import lombok.val;

@SuppressLint("NonConstantResourceId")
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authTokenPreferences = AuthTokenPreferences.builder()
                .context(this)
                .build();
        userPreferences = UserPreferences.builder()
                .context(this)
                .build();
        phoneNumberPreferences = PhoneNumberPreferences.builder()
                .context(this)
                .build();

        configureMenuOptions();
        settingUpViewPager();

    }

    protected void settingUpViewPager() {
        val viewPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatsFragment(), "CHATS");
        viewPagerAdapter.addFragment(ContactsFragment.newInstance(this), "CONTACTS");

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


}