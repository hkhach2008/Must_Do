            package com.example.must_do_002;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {
    private Button to_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        to_sign_up = findViewById(R.id.to_sign_up);
        to_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_sign_up = new Intent(SignIn.this, SignUp.class);
                startActivity(profile_sign_up);
            }
        });
    }
}
