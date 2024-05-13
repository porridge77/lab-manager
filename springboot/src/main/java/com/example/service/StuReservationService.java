package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ReserveEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.StatusEnum;
import com.example.entity.Account;
import com.example.entity.Lab;
import com.example.entity.StuReservation;
import com.example.mapper.LabMapper;
import com.example.mapper.StuReservationMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StuReservationService {
    @Resource
    private StuReservationMapper reserveMapper;
    @Resource
    private LabMapper labMapper;
    @Resource
    private SemesterService semesterService;

    public void add(StuReservation reserve) {
        reserve.setSemesterName(semesterService.getCurrentSemester());
        reserve.setDate(DateUtil.date());
        reserve.setStatus(ReserveEnum.EMP.status);
        reserveMapper.insert(reserve);
    }

    public void deleteById(Integer id) {
        reserveMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            reserveMapper.deleteById(id);
        }
    }

    public void updateById(StuReservation reserve) {
        reserveMapper.updateById(reserve);
    }

    public StuReservation selectById(Integer id) {
        return reserveMapper.selectById(id);
    }

    public List<StuReservation> selectAll(StuReservation reserve) {
        return reserveMapper.selectAll(reserve);
    }

    public PageInfo<StuReservation> selectPage(StuReservation reserve, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            reserve.setStudentId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<StuReservation> list = reserveMapper.selectAll(reserve);
        return PageInfo.of(list);
    }
}
