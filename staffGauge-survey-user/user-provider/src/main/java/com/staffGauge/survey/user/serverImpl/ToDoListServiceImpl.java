package com.staffGauge.survey.user.serverImpl;

import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.user.api.ApiToDoListService;
import com.staffGauge.survey.user.dal.persistence.ToDoListMapper;
import com.staffGauge.survey.user.dao.BaseToDoList;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ToDoListServiceImpl implements ApiToDoListService {
    @Autowired
    private ToDoListMapper toDoListMapper;

    @Override
    public PageInfo<BaseToDoList> selectToDoList(String username) {
        List<BaseToDoList> toDoList = toDoListMapper.selectToDoLists(username);
        PageInfo<BaseToDoList> pageInfo = new PageInfo<>(toDoList);
        return pageInfo;
    }

    @Override
    public boolean updateToDoList(String username, String eventDate, String eventTitle, String eventDesc) {
        if (toDoListMapper.updateToDoList(username, eventDate, eventTitle, eventDesc) == 1) return true;
        else return insertToDoList(username, eventDate, eventTitle, eventDesc);
    }

    @Override
    public boolean deleteToDoList(String username, String eventDate, String eventTitle, String eventDesc) {
        return toDoListMapper.deleteToDoList(username, eventDate, eventTitle, eventDesc) == 1 ? true : false;
    }

    @Override
    public boolean insertToDoList(String username, String eventDate, String eventTitle, String eventDesc) {
        return toDoListMapper.insertToDoList(username, eventDate, eventTitle, eventDesc) == 1 ? true : false;
    }
}
