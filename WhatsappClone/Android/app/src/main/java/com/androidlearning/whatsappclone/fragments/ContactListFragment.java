package com.androidlearning.whatsappclone.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.activities.ChatActivity_;
import com.androidlearning.whatsappclone.adapters.ContactsAdapter;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.ErrorBody;
import com.androidlearning.whatsappclone.helpers.GsonHelper;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.helpers.preferences.UserPreferences;
import com.androidlearning.whatsappclone.inputs.AddContactInput;
import com.androidlearning.whatsappclone.models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.androidlearning.whatsappclone.services.UserService;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@NoArgsConstructor
public class ContactListFragment extends Fragment {


    protected FloatingActionButton mAddContactButton;
    protected ListView mContactsList;
    protected ArrayAdapter mContactsAdapter;
    protected List<UserModel> mContacts = new ArrayList<UserModel>();

    @NonNull
    private Context activityContext;
    private AuthTokenPreferences authTokenPreferences;
    private UserPreferences userPreferences;
    private UserService userService;

    public static ContactListFragment newInstance(@NonNull Context context) {
        ContactListFragment fragment = new ContactListFragment();
        fragment.activityContext = context;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authTokenPreferences = AuthTokenPreferences.builder()
                .context(activityContext)
                .build();

        userService = RetrofitServiceFactory.create(UserService.class);

        fetchContacts();
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        val view = inflater.inflate(R.layout.fragment_contacts, container, false);


        mAddContactButton = view.findViewById(R.id.add_contact);
        mAddContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactRegister();
            }
        });

        userPreferences = UserPreferences.builder()
                .context(getContext())
                .build();

        mContacts.add(userPreferences.getUserData());


        mContactsList = view.findViewById(R.id.contacts_list);
        mContactsAdapter = new ContactsAdapter(
                activityContext,
                mContacts
        );

        mContactsList.setAdapter(mContactsAdapter);
        mContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                val contact = mContacts.get(position);
                val contactGson = GsonHelper.toGson(contact);

                val intent = new Intent(activityContext, ChatActivity_.class);
                intent.putExtra("contact", contactGson);
                startActivity(intent);
            }
        });

        return view;
    }


    protected void openContactRegister() {
        val dialogBuilder = new AlertDialog.Builder(activityContext);

        val editText = new EditText(activityContext);

        val dialog = dialogBuilder
                .setTitle("New Contact")
                .setMessage("Insert user's email")
                .setCancelable(false)
                .setView(editText)
                .setPositiveButton("ADD", null)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val email = editText.getText().toString();
                addContact(email, dialog);
            }
        });
    }

    protected void addContact(String email, AlertDialog dialog) {
        val token = authTokenPreferences.getToken();

        val input = AddContactInput.builder()
                .email(email)
                .build();

        val call = userService.addContact(input, token);

        call.enqueue(new Callback<UserModel>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(!response.isSuccessful()) {
                    val error = ErrorBody.fromResponse(response);
                    Toast.makeText(activityContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                val contact = response.body();
                mContacts.add(contact);

                Toast.makeText(activityContext, "User added to contacts", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("err", t.getMessage());
            }
        });
    }

    protected void fetchContacts() {
        val token = authTokenPreferences.getToken();
        val call = userService.getContacts(token);

        call.enqueue(new Callback<List<UserModel>>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                if(!response.isSuccessful()) {
                    Log.e("here", "error given");
                    Log.e("err", response.errorBody().string());
                    return;
                }

                mContacts.clear();

                val contacts = response.body();
                assert contacts != null;

                mContacts.addAll(contacts);
                mContactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.e("err", t.getMessage());
            }
        });
    }
}