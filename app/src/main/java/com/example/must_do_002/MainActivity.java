package com.example.must_do_002;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.must_do_002.Adapter.ToDoAdapter;
import com.example.must_do_002.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddNewTask.TaskSaveListener {
    private Button btn_profile;
    private Button button_add_task;
    private RecyclerView tasks_recycler_view;
    private ToDoAdapter tasks_adapter;
    private List<ToDoModel> task_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task_list = new ArrayList<>();
        btn_profile = findViewById(R.id.button_profile);
        button_add_task = findViewById(R.id.button_add_task);
        tasks_recycler_view = findViewById(R.id.tasks_recycler_view);

        tasks_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        tasks_adapter = new ToDoAdapter(task_list);
        tasks_recycler_view.setAdapter(tasks_adapter);

        button_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAddTask(v);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MainActivity.this, Profile.class);
                startActivity(profile);
            }
        });

        // Load initial tasks if any
        populateInitialTasks();
    }

    private void populateInitialTasks() {
        // You can populate initial tasks here if needed
    }

    private void showPopupAddTask(View v) {
        PopupMenu popup = new PopupMenu(MainActivity.this, v);
        popup.getMenuInflater().inflate(R.menu.popup_add_task, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.popup_school) {
                    showAddNewTaskDialog();
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private void showAddNewTaskDialog() {
        AddNewTask addNewTaskDialog = AddNewTask.newInstance();
        addNewTaskDialog.setTaskSaveListener(this);
        addNewTaskDialog.show(getSupportFragmentManager(), AddNewTask.TAG);
    }

    @Override
    public void onTaskSave(ToDoModel newTask) {
        tasks_adapter.addTask(newTask);
    }
}
