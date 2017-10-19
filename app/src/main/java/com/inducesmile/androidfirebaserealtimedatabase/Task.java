package com.inducesmile.androidfirebaserealtimedatabase;

/**
 * Created by jrwoj on 17.10.2017.
 */

public class Task {
    private String task;
    private String description;

    public Task() {
        this.description = "Hardcoded desc";
    }
    public Task(String task) {
        this.task = task;
        this.description = "Hardcoded desc";
    }


    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }
}
