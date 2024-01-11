package com.example.must_do_002;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class Profile extends AppCompatActivity{
    private Button btn_profile_sign_in, btn_profile_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        btn_profile_sign_in = findViewById(R.id.profile_sign_in);
        btn_profile_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_sign_in = new Intent(Profile.this, SignIn.class);
                startActivity(profile_sign_in);
            }
        });
        btn_profile_sign_up = findViewById(R.id.profile_sign_up);
        btn_profile_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_sign_up = new Intent(Profile.this, SignUp.class);
                startActivity(profile_sign_up);
            }
        });
    }
}