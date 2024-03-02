package com.example.must_do_002.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.must_do_002.Model.ToDoModel;
import com.example.must_do_002.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> todoList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(item.getStatus() != 0);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    // Method to set new list of tasks
    public void setTasks(List<ToDoModel> newTodoList) {
        todoList.clear(); // Clear existing list
        todoList.addAll(newTodoList); // Add new list
        notifyDataSetChanged(); // Notify the adapter to update the RecyclerView
    }


    // ViewHolder class for the layout of each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.task_checkbox);
        }
    }
}
