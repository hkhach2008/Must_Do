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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText sign_up_email, sign_up_username, sign_up_password;
    Button btn_sign_up_button;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        sign_up_email = findViewById(R.id.sign_up_email);
        sign_up_username = findViewById(R.id.sign_up_username);
        sign_up_password = findViewById(R.id.sign_up_password);
        btn_sign_up_button = findViewById(R.id.btn_sign_up_button);
        btn_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserSignUp();
            }
        });
    }

    private void startUserSignUp()
    {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        String username = sign_up_username.getText().toString();
        String email = sign_up_email.getText().toString();
        String password = sign_up_password.getText().toString();
        if(username.isEmpty())
            Toast.makeText(this, "Please enter username!", Toast.LENGTH_SHORT).show();
        else if(email.isEmpty())
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            Toast.makeText(this, "Please enter proper email address!", Toast.LENGTH_SHORT).show();
        else if(password.isEmpty())
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
        else if(username.isEmpty())
            Toast.makeText(this, "Please enter username!", Toast.LENGTH_SHORT).show();
        else
        {
            User user = new User(username, email, password);
            reference.child(username).setValue(user);
            Toast.makeText(SignUp.this, "You have sign up successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        }
    }
}
