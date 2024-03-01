package com.example.must_do_002;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignIn;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in); // Ensure you have this layout

        databaseHelper = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.sign_in_email);
        editTextPassword = findViewById(R.id.sign_in_password);
        buttonSignIn = findViewById(R.id.sign_in_button);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignIn.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkUser(email, password)) {
            saveLoginSession(email); // Save user session
            Toast.makeText(SignIn.this, "Login successful", Toast.LENGTH_SHORT).show();
            // Navigate to Profile activity or another activity as needed
            Intent intent = new Intent(SignIn.this, Profile.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SignIn.this, "Login error: check your credentials", Toast.LENGTH_LONG).show();
        }
    }

    // Method to save user login session data
    private void saveLoginSession(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserEmail", email);
        editor.apply();
    }
}
