package com.example.service;

import com.example.entity.Semester;
import com.example.mapper.SemesterMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学期信息业务处理
 */
@Service
public class SemesterService {
    @Resource
    private SemesterMapper semesterMapper;

    /**
     * 新增
     */
    public void add(Semester semester) {
        semesterMapper.insert(semester);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(Integer id) {
        semesterMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            semesterMapper.deleteById(id);
        }
    }

    /**
     * 根据ID查询
     */
    public Semester selectById(Integer id) {
        return semesterMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Semester> selectAll(Semester semester) {
        return semesterMapper.selectAll(semester);
    }

    /**
     * 分页查询
     */
    public PageInfo<Semester> selectPage(Semester semester, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Semester> list = semesterMapper.selectAll(semester);
        return PageInfo.of(list);
    }

    /**
     * 设置当前学期
     * @param id
     */
    public void setCurrentSemester(Integer id) {
        semesterMapper.resetCurrentSemester();
        semesterMapper.setCurrentSemester(id);
    }

    /**
     * 查询当前学期
     * @return
     */
    public String getCurrentSemester() {
        return semesterMapper.getCurrentSemester();
    }
}
