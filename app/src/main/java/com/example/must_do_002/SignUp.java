package com.example.must_do_002;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    FirebaseAuth m_auth;
    private EditText sign_up_email, sign_up_username, sign_up_password;
    private Button btn_sign_up_button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        m_auth = FirebaseAuth.getInstance();
        sign_up_email = findViewById(R.id.sign_up_email);
        sign_up_username = findViewById(R.id.sign_up_username);
        sign_up_password = findViewById(R.id.sign_up_password);
        btn_sign_up_button = findViewById(R.id.btn_sign_up_button);
        btn_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserRegistration();
            }
        });
    }

    private void startUserRegistration()
    {
        String username = sign_up_username.getText().toString().trim();
        String email = sign_up_email.getText().toString().trim();
        String password = sign_up_password.getText().toString().trim();
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
            m_auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                User user = new User(username, email, password);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(SignUp.this, "User failed to register", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else
                                Toast.makeText(SignUp.this, "User failed to register", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
