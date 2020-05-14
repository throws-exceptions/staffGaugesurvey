package com.staffGauge.survey.user.api;

import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.user.dao.BaseToDoList;

public interface ApiToDoListService {
    PageInfo<BaseToDoList> selectToDoList(String username);

    boolean updateToDoList(String username, String eventDate, String eventTitle, String eventDesc);

    boolean deleteToDoList(String username, String eventDate, String eventTitle, String eventDesc);

    boolean insertToDoList(String username, String eventDate, String eventTitle, String eventDesc);

}
