package com.example.must_do_002;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpFragment extends Fragment {

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonSignUp;
    private DatabaseHelper databaseHelper;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        editTextName = view.findViewById(R.id.sign_up_username);
        editTextEmail = view.findViewById(R.id.sign_up_email);
        editTextPassword = view.findViewById(R.id.sign_up_password);
        buttonSignUp = view.findViewById(R.id.btn_sign_up);

        buttonSignUp.setOnClickListener(v -> registerUser());

        return view;
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(name, email, password);
            databaseHelper.addUser(user);
            Toast.makeText(getContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
            // Optionally navigate back to sign-in or profile
        }
    }
}
