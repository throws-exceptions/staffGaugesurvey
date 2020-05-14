package com.staffGauge.survey.user.dal.persistence;

import com.staffGauge.survey.user.dao.BaseToDoList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ToDoListMapper {
    Integer insertToDoList(@Param("username") String username,
                           @Param("eventDate") String eventDate,
                           @Param("eventTitle") String eventTitle,
                           @Param("eventDesc") String eventDesc);

    List<BaseToDoList> selectToDoLists(@Param("username") String username);

    Integer deleteToDoList(@Param("username") String username,
                           @Param("eventDate") String eventDate,
                           @Param("eventTitle") String eventTitle,
                           @Param("eventDesc") String eventDesc);

    Integer updateToDoList(@Param("username") String username,
                           @Param("eventDate") String eventDate,
                           @Param("eventTitle") String eventTitle,
                           @Param("eventDesc") String eventDesc);
}
