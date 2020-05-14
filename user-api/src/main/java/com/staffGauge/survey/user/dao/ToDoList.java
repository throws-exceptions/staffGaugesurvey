package com.staffGauge.survey.user.dao;

import javax.persistence.Table;
import java.io.Serializable;

@Table(schema = "tb_todolist")
public class ToDoList extends BaseToDoList implements Serializable {
    private static final long serialVersionUID = 2473819957046433663L;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
