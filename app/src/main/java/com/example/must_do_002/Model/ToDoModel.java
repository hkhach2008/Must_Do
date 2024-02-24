package com.example.must_do_002.Model;

public class ToDoModel {
    private int id;
    private int status; // Consider using boolean for done/not done status
    private String task;

    // No-argument constructor required for Firebase
    public ToDoModel() {
    }

    // Constructor with parameters for easier instantiation
    public ToDoModel(int id, int status, String task) {
        this.id = id;
        this.status = status;
        this.task = task;
    }

    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Getter and setter for task
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
