package com.example.mapper;

import com.example.entity.Semester;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 操作Semester相关数据接口
 */
public interface SemesterMapper {

    void insert(Semester semester);

    void deleteById(Integer id);

    Semester selectById(Integer id);

    List<Semester> selectAll(Semester semester);

    @Update("UPDATE semester SET is_current = 0")
    void resetCurrentSemester();
    @Update("UPDATE semester SET is_current = 1 WHERE id = #{id}")
    void setCurrentSemester(Integer id);
    @Select("SELECT name FROM semester WHERE is_current = 1")
    String getCurrentSemester();
}
