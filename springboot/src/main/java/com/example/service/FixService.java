package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.FixEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Fix;
import com.example.entity.Lab;
import com.example.mapper.FixMapper;
import com.example.mapper.LabMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FixService {

    @Resource
    private FixMapper fixMapper;
    @Resource
    private LabMapper labMapper;


    /**
     * 新增
     */
    public void add(Fix fix) {
        fix.setDate(DateUtil.date());
        fix.setStatus(FixEnum.NOT.status);
        fixMapper.insert(fix);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        fixMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            fixMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Fix fix) {
        fix.setLabId(labMapper.getIdByNo(fix.getLabNo()));
        fixMapper.updateById(fix);
    }

    /**
     * 根据ID查询
     */
    public Fix selectById(Integer id) {
        return fixMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Fix> selectAll(Fix fix) {
        return fixMapper.selectAll(fix);
    }

    /**
     * 分页查询
     */
    public PageInfo<Fix> selectPage(Fix fix, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
            fix.setTeacherId(currentUser.getId());
        }
        if (RoleEnum.LABADMIN.name().equals(currentUser.getRole())) {
            fix.setLabAdminId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Fix> list = fixMapper.selectAll(fix);
        return PageInfo.of(list);
    }

}
