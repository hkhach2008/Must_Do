package com.example.must_do_002;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.must_do_002.Adapter.ToDoAdapter;
import com.example.must_do_002.Model.ToDoModel;

import java.util.List;

public class TasksFragment extends Fragment implements AddNewTask.TaskSaveListener {
    private Button buttonAddTask;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private DatabaseHelper databaseHelper;
    private String currentUserEmail;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        databaseHelper = new DatabaseHelper(getActivity());
        buttonAddTask = view.findViewById(R.id.button_add_task);
        tasksRecyclerView = view.findViewById(R.id.tasks_recycler_view);

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksAdapter = new ToDoAdapter();
        tasksRecyclerView.setAdapter(tasksAdapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupAddTask(v);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateInitialTasks();
    }

    private void populateInitialTasks() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginSession", getActivity().MODE_PRIVATE);
        currentUserEmail = sharedPreferences.getString("UserEmail", "");
        if (!currentUserEmail.isEmpty()) {
            List<ToDoModel> tasks = databaseHelper.getAllTasksForUser(currentUserEmail);
            tasksAdapter.setTasks(tasks); // This should clear and update the adapter's data
        }
    }

    private void showPopupAddTask(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
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
        addNewTaskDialog.show(getParentFragmentManager(), AddNewTask.TAG);
    }

    @Override
    public void onTaskSave(ToDoModel newTask) {
        databaseHelper.addTask(newTask.getTask(), newTask.getStatus(), currentUserEmail);
        populateInitialTasks(); // Call this to refresh the list from the database
    }
}
