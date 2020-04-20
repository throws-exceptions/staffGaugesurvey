package com.staffGauge.survey.user.dao;

import javax.persistence.Column;

public class BaseToDoList {
    @Column(name = "event_date")
    private String date;
    @Column(name = "event_title")
    private String title;
    @Column(name = "event_desc")
    private String desc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
