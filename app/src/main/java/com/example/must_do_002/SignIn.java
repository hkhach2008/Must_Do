package com.example.must_do_002;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignIn extends AppCompatActivity {
    private Button to_sign_up;
    EditText sign_in_email, sign_in_password;
    Button sign_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        to_sign_up = findViewById(R.id.to_sign_up);
        sign_in_email = findViewById(R.id.sign_in_email);
        sign_in_password = findViewById(R.id.sign_in_password);
        sign_in_button = findViewById(R.id.sign_in_button);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserLogin();
            }
        });

        to_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_sign_up = new Intent(SignIn.this, SignUp.class);
                startActivity(profile_sign_up);
            }
        });
    }
    private void startUserLogin()
    {
        String email = sign_in_email.getText().toString().trim();
        String password = sign_in_password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query check_email_database = reference.orderByChild("email").equalTo(email);

        if(email.isEmpty())
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            Toast.makeText(this, "Please enter proper email address!", Toast.LENGTH_SHORT).show();
        else if(password.isEmpty())
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
        else
        {
            check_email_database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        sign_in_email.setError(null);
                        String password_from_db = snapshot.child(email).child("password").getValue(String.class);

                        if(!Objects.equals(password_from_db, password))
                        {
                            sign_in_email.setError(null);
                            Intent intent = new Intent(SignIn.this, MainActivity.class);
                        }
                        else
                        {
                            sign_in_password.setError("Invalid Credentials");
                            sign_in_password.requestFocus();
                        }
                    }
                    else
                    {
                        sign_in_email.setError("no account with this email!");
                        sign_in_email.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
