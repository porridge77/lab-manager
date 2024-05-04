package com.example.mapper;

import com.example.entity.Lesson;

import java.util.List;

public interface LessonMapper {
    void insert(Lesson lesson);

    void deleteById(Integer id);

    void updateById(Lesson lesson);

    Lesson selectById(Integer id);

    List<Lesson> selectAll(Lesson lesson);

}
