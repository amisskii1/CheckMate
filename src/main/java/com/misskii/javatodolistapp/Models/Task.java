package com.misskii.javatodolistapp.Models;

import java.sql.Date;

public class Task {
    private String taskTitle;
    private String taskDescription;
    private Date date;
    private int personId;
    private int taskId;
    private String status;

    public Task() {
    }

    public Task(String taskTitle, String taskDescription, Date date, int personId, String status){
        this.status = status;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.date = date;
        this.personId = personId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPersonId() {
        return personId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }
}
