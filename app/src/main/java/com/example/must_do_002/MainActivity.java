package com.example.must_do_002;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.must_do_002.Adapter.ToDoAdapter;
import com.example.must_do_002.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_profile;
    private Button button_add_task;
    private RecyclerView tasks_recycler_view;
    private ToDoAdapter tasks_adapter;
    private List<ToDoModel> task_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task_list = new ArrayList<>();

        btn_profile = findViewById(R.id.button_profile);
        button_add_task = findViewById(R.id.button_add_task);
        tasks_recycler_view = findViewById(R.id.tasks_recycler_view);
        tasks_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        tasks_adapter = new ToDoAdapter(this);
        tasks_recycler_view.setAdapter(tasks_adapter);

        ToDoModel task = new ToDoModel();
        task.setTask("This is Task Task");
        task.setStatus(0);
        task.setId(1);
        task_list.add(task);
        task_list.add(task);
        task_list.add(task);
        task_list.add(task);
        task_list.add(task);

        tasks_adapter.setTasks(task_list);
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