package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.LessonEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.TeaApplication;
import com.example.mapper.LabMapper;
import com.example.mapper.TeaApplicationMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeaApplicationService {
    @Resource
    private TeaApplicationMapper applyMapper;
    @Resource
    private LabMapper labMapper;
    @Resource
    private SemesterService semesterService;

    public void add(TeaApplication apply) {
        apply.setSemesterName(semesterService.getCurrentSemester());
        apply.setStatus(LessonEnum.NO.status);
        applyMapper.insert(apply);
    }

    public void deleteById(Integer id) {
        applyMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            applyMapper.deleteById(id);
        }
    }

    public void updateById(TeaApplication apply) {
        applyMapper.updateById(apply);
    }

    public TeaApplication selectById(Integer id) {
        return applyMapper.selectById(id);
    }

    public List<TeaApplication> selectAll(TeaApplication apply) {
        return applyMapper.selectAll(apply);
    }

    public PageInfo<TeaApplication> selectPage(TeaApplication apply, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
            apply.setTeacherId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TeaApplication> list = applyMapper.selectAll(apply);
        return PageInfo.of(list);
    }


    public int arrangeLabId(String type, Integer number) {
        return labMapper.arrangeLabId(type, number);
    }
}
