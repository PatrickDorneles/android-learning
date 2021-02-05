package com.androidlearning.whatsappclone.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidlearning.whatsappclone.R;
import com.androidlearning.whatsappclone.factories.RetrofitServiceFactory;
import com.androidlearning.whatsappclone.helpers.ErrorBody;
import com.androidlearning.whatsappclone.helpers.preferences.AuthTokenPreferences;
import com.androidlearning.whatsappclone.inputs.AddContactInput;
import com.androidlearning.whatsappclone.models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.androidlearning.whatsappclone.services.UserService;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@NoArgsConstructor
public class ContactsFragment extends Fragment {


    protected FloatingActionButton mAddContactButton;
    protected ListView mContactsList;
    protected ListAdapter mListAdapter;

    private Context activityContext;
    private AuthTokenPreferences authTokenPreferences;
    private UserService userService;

    public static ContactsFragment newInstance(Context context) {
        ContactsFragment fragment = new ContactsFragment();
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
    }

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

        mContactsList = (ListView) view.findViewById(R.id.contacts_list);
        mListAdapter

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
                .setPositiveButton("REGISTER", null)
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
                addContact(email);
            }
        });
    }

    protected void addContact(String email) {
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


                Toast.makeText(activityContext, "User added to contacts", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("err", t.getMessage());
            }
        });
    }
}