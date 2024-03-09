package com.example.must_do_002;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class SignInFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;
    private DatabaseHelper databaseHelper;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        editTextEmail = view.findViewById(R.id.sign_in_email);
        editTextPassword = view.findViewById(R.id.sign_in_password);
        buttonSignIn = view.findViewById(R.id.sign_in_button);

        buttonSignIn.setOnClickListener(v -> loginUser());

        return view;
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please enter your credentials", Toast.LENGTH_SHORT).show();
        } else {
            if (databaseHelper.checkUser(email, password)) {
                saveLoginSession(email);
                Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_signInFragment_to_profileFragment);
            } else {
                Toast.makeText(getContext(), "Login error: check your credentials", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveLoginSession(String email) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginSession", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserEmail", email);
        editor.apply();
    }
}
