package com.example.mapper;

import com.example.entity.StuReservation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StuReservationMapper {
    void insert(StuReservation reserve);

    StuReservation selectById(Integer id);

    void deleteById(Integer id);

    void updateById(StuReservation reserve);

    List<StuReservation> selectAll(StuReservation reserve);

}
