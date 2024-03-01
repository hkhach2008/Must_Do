package com.example.must_do_002;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    private Button btn_profile_sign_in, btn_profile_sign_up, btn_profile_sign_out;
    private TextView profile_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Initialize UI components
        btn_profile_sign_in = findViewById(R.id.profile_sign_in);
        btn_profile_sign_up = findViewById(R.id.profile_sign_up);
        btn_profile_sign_out = findViewById(R.id.profile_sign_out);
        profile_username = findViewById(R.id.profile_username);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("UserEmail", "");

        if (!userEmail.isEmpty()) {
            // User is logged in
            DatabaseHelper db = new DatabaseHelper(this);
            String username = db.getUserNameByEmail(userEmail);
            profile_username.setText(username);
            profile_username.setVisibility(View.VISIBLE);
            btn_profile_sign_out.setVisibility(View.VISIBLE);
            btn_profile_sign_in.setVisibility(View.GONE);
            btn_profile_sign_up.setVisibility(View.GONE);

            btn_profile_sign_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    recreate(); // Refresh the activity to update UI
                }
            });
        } else {
            // No user is logged in
            btn_profile_sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent profile_sign_in = new Intent(Profile.this, SignIn.class);
                    startActivity(profile_sign_in);
                }
            });

            btn_profile_sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent profile_sign_up = new Intent(Profile.this, SignUp.class);
                    startActivity(profile_sign_up);
                }
            });
        }
    }

    // Method to save user login session data
    public void saveLoginSession(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserEmail", email);
        editor.apply();
    }
}
