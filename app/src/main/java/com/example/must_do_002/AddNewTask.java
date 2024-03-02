package com.example.must_do_002;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.must_do_002.Model.ToDoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText new_task_text;
    private Button new_task_save_button;
    private String userEmail;

    // Define an interface for the callback
    public interface TaskSaveListener {
        void onTaskSave(ToDoModel newTask);
    }

    private TaskSaveListener taskSaveListener;

    // Setter for the listener
    public void setTaskSaveListener(TaskSaveListener taskSaveListener) {
        this.taskSaveListener = taskSaveListener;
    }

    // Static method to create a new instance of AddNewTask and pass userEmail
    public static AddNewTask newInstance(String userEmail) {
        AddNewTask fragment = new AddNewTask();
        Bundle args = new Bundle();
        args.putString("userEmail", userEmail); // Pass the userEmail to the fragment
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
        if (getArguments() != null) {
            userEmail = getArguments().getString("userEmail", ""); // Retrieve the userEmail
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new_task_text = view.findViewById(R.id.new_task_text);
        new_task_save_button = view.findViewById(R.id.new_task_button);
        new_task_save_button.setEnabled(false); // Initially disable the save button until text is entered

        new_task_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    new_task_save_button.setEnabled(false);
                    new_task_save_button.setTextColor(Color.GRAY);
                } else {
                    new_task_save_button.setEnabled(true);
                    new_task_save_button.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        new_task_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = new_task_text.getText().toString().trim();
                if (!taskText.isEmpty() && taskSaveListener != null) {
                    ToDoModel newTask = new ToDoModel();
                    newTask.setTask(taskText);
                    newTask.setStatus(0); // Assuming 0 is the status for a new (incomplete) task
                    taskSaveListener.onTaskSave(newTask);

                    // Save the task to the database
                    DatabaseHelper db = new DatabaseHelper(getContext());
                    db.addTask(taskText, 0, userEmail); // Saving the task associated with the userEmail

                    dismiss(); // Close the BottomSheetDialogFragment
                }
            }
        });
    }
}
