package com.example.must_do_002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    private Button button_add_task;
    private PopupWindow popupWindow;
    private Button btn_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_add_task = findViewById(R.id.button_add_task);
        button_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the popup_menu.xml layout file
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_menu, null);

                // Create the PopupWindow object
                popupWindow = new PopupWindow(
                        popupView,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                        true
                );

                // Set an elevation value for popup window
                popupWindow.setElevation(10);

                // Show the popup window
//                popupWindow.showAsDropDown(button_add_task, 0, 0);
                int[] location = new int[2];
                button_add_task.getLocationOnScreen(location);
                popupWindow.showAtLocation(button_add_task, Gravity.NO_GRAVITY, location[0],location[1] - popupWindow.getHeight());
            }
        });

        btn_profile = findViewById(R.id.button_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MainActivity.this, Profile.class);
                startActivity(profile);
            }
        });
    }
}