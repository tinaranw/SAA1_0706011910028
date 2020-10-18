package com.uc.saa1_0706011910028.model;

public class Task {
    private String id;
    private String title;
    private String desc;
    private String duedate;
    private String tasktype;

    public Task(){}

    public Task(String id, String title, String desc, String duedate, String tasktype) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.duedate = duedate;
        this.tasktype = tasktype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }
}
