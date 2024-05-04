package com.example.mapper;

import com.example.entity.TeaApplication;

import java.util.List;

public interface TeaApplicationMapper {
    void insert(TeaApplication apply);

    TeaApplication selectById(Integer id);

    void deleteById(Integer id);

    void updateById(TeaApplication apply);

    List<TeaApplication> selectAll(TeaApplication apply);
}
