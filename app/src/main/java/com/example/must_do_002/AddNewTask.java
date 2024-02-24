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

    // Define an interface for the callback
    public interface TaskSaveListener {
        void onTaskSave(ToDoModel newTask);
    }

    private TaskSaveListener taskSaveListener;

    // Setter for the listener
    public void setTaskSaveListener(TaskSaveListener taskSaveListener) {
        this.taskSaveListener = taskSaveListener;
    }

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
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

        boolean isUpdate = false; // This flag could be used if you're planning to use this dialog for editing tasks as well

        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true; // Assuming you're passing a flag or data to indicate an update operation
            String task = bundle.getString("task", ""); // Using default value to avoid null
            new_task_text.setText(task);
            if (!task.isEmpty()) {
                new_task_save_button.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
            }
        }

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
                    dismiss(); // Close the BottomSheetDialogFragment
                }
            }
        });
    }
}
