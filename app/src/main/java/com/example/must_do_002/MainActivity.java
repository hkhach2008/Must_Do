package com.example.must_do_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {
    private Button btn_profile;
    private Button button_add_task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_profile = findViewById(R.id.button_profile);
        button_add_task = findViewById(R.id.button_add_task);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MainActivity.this, Profile.class);
                startActivity(profile);
            }
        });

        button_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAddTask(v);
            }
        });
    }

    private void showPopupAddTask(View v)
    {
        PopupMenu popup = new PopupMenu(MainActivity.this, v);
        popup.getMenuInflater().inflate(R.menu.popup_add_task, popup.getMenu());
        popup.show();
    }
}