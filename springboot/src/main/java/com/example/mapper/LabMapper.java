package com.example.mapper;

import com.example.entity.Lab;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LabMapper {

    /**
     * 新增
     */
    int insert(Lab lab);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Lab lab);

    /**
     * 根据ID查询
     */
    Lab selectById(Integer id);

    /**
     * 查询所有
     */
    List<Lab> selectAll(Lab lab);

    @Select("SELECT id FROM lab WHERE no = #{labNo}")
    Integer getIdByNo(String labNo);

    @Select("SELECT id FROM lab WHERE type= #{type} and equipment_num >= #{number} limit 1")
    Integer arrangeLabId(String type, Integer number);

}
