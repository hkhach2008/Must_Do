package com.example.must_do_002;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.must_do_002.Adapter.ToDoAdapter;
import com.example.must_do_002.Model.ToDoModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddNewTask.TaskSaveListener {
    private Button btn_profile;
    private Button button_add_task;
    private RecyclerView tasks_recycler_view;
    private ToDoAdapter tasks_adapter;

    private DatabaseHelper databaseHelper;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        btn_profile = findViewById(R.id.button_profile);
        button_add_task = findViewById(R.id.button_add_task);
        tasks_recycler_view = findViewById(R.id.tasks_recycler_view);

        tasks_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        tasks_adapter = new ToDoAdapter();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateInitialTasks();
    }

    private void populateInitialTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE);
        currentUserEmail = sharedPreferences.getString("UserEmail", "");
        if (!currentUserEmail.isEmpty()) {
            List<ToDoModel> tasks = databaseHelper.getAllTasksForUser(currentUserEmail);
            tasks_adapter.setTasks(tasks); // This should clear and update the adapter's data
        }
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
                // Add more cases for other task categories if needed
                return false;
            }
        });
        popup.show();
    }

    private void showAddNewTaskDialog() {
        AddNewTask addNewTaskDialog = AddNewTask.newInstance(currentUserEmail);
        addNewTaskDialog.setTaskSaveListener(this);
        addNewTaskDialog.show(getSupportFragmentManager(), AddNewTask.TAG);
    }

    @Override
    public void onTaskSave(ToDoModel newTask) {
        // Do not add newTask to tasks_adapter here
        databaseHelper.addTask(newTask.getTask(), newTask.getStatus(), currentUserEmail);
        populateInitialTasks(); // Call this to refresh the list from the database
    }
}
