package com.example.must_do_002;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ProfileFragment extends Fragment {

    private Button btnProfileSignIn, btnProfileSignUp, btnProfileSignOut;
    private TextView profileUsername;
    private DatabaseHelper databaseHelper;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        databaseHelper = new DatabaseHelper(getContext());

        btnProfileSignIn = view.findViewById(R.id.profile_sign_in);
        btnProfileSignUp = view.findViewById(R.id.profile_sign_up);
        btnProfileSignOut = view.findViewById(R.id.profile_sign_out);
        profileUsername = view.findViewById(R.id.profile_username);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginSession", getActivity().MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("UserEmail", "");

        updateUIBasedOnUserStatus(userEmail);

        btnProfileSignOut.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            updateUIBasedOnUserStatus(null); // Reset UI
        });

        btnProfileSignIn.setOnClickListener(v -> navigateToSignIn());
        btnProfileSignUp.setOnClickListener(v -> navigateToSignUp());

        return view;
    }

    private void updateUIBasedOnUserStatus(String userEmail) {
        if (userEmail != null && !userEmail.isEmpty()) {
            String username = databaseHelper.getUserNameByEmail(userEmail);
            profileUsername.setText(username);
            profileUsername.setVisibility(View.VISIBLE);
            btnProfileSignOut.setVisibility(View.VISIBLE);
            btnProfileSignIn.setVisibility(View.GONE);
            btnProfileSignUp.setVisibility(View.GONE);
        } else {
            profileUsername.setVisibility(View.GONE);
            btnProfileSignOut.setVisibility(View.GONE);
            btnProfileSignIn.setVisibility(View.VISIBLE);
            btnProfileSignUp.setVisibility(View.VISIBLE);
        }
    }

    private void navigateToSignIn() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.signInFragment);
    }

    private void navigateToSignUp() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.signUpFragment);
    }
}
