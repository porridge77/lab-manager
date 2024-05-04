package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Teacher;
import com.example.exception.CustomException;
import com.example.mapper.TeacherMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师业务处理
 **/
@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 新增
     */
    public void add(Teacher labAdmin) {
        Teacher dbLabAdmin = teacherMapper.selectByAccount(labAdmin.getAccount());
        if (ObjectUtil.isNotNull(dbLabAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(labAdmin.getPassword())) {
            labAdmin.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(labAdmin.getName())) {
            labAdmin.setName(labAdmin.getAccount());
        }
        labAdmin.setRole(RoleEnum.TEACHER.name());
        teacherMapper.insert(labAdmin);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        teacherMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            teacherMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Teacher labAdmin) {
        teacherMapper.updateById(labAdmin);
    }

    /**
     * 根据ID查询
     */
    public Teacher selectById(Integer id) {
        return teacherMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Teacher> selectAll(Teacher labAdmin) {
        return teacherMapper.selectAll(labAdmin);
    }

    /**
     * 分页查询
     */
    public PageInfo<Teacher> selectPage(Teacher labAdmin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> list = teacherMapper.selectAll(labAdmin);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    public Account login(Account account) {
        Account dbLabAdmin = teacherMapper.selectByAccount(account.getAccount());
        if (ObjectUtil.isNull(dbLabAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbLabAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbLabAdmin.getId() + "-" + RoleEnum.TEACHER.name();
        String token = TokenUtils.createToken(tokenData, dbLabAdmin.getPassword());
        dbLabAdmin.setToken(token);
        return dbLabAdmin;
    }

    /**
     * 注册
     */
    public void register(Account account) {
        Teacher labAdmin = new Teacher();
        BeanUtils.copyProperties(account, labAdmin);
        add(labAdmin);
    }

    /**
     * 修改密码
     */
    public void updatePassword(Account account) {
        Teacher dbLabAdmin = teacherMapper.selectByAccount(account.getAccount());
        if (ObjectUtil.isNull(dbLabAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbLabAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbLabAdmin.setPassword(account.getNewPassword());
        teacherMapper.updateById(dbLabAdmin);
    }

}