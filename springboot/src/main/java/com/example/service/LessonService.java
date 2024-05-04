package com.example.service;

import com.example.common.enums.LessonEnum;
import com.example.entity.Lab;
import com.example.entity.Lesson;
import com.example.entity.TeaApplication;
import com.example.mapper.LessonMapper;
import com.example.mapper.SemesterMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service
public class LessonService {
    @Resource
    private LessonMapper lessonMapper;
    @Resource
    private SemesterMapper semesterMapper;
    @Resource
    private TeaApplicationService teaApplicationService;

    public void add(Integer id) {
        TeaApplication teaApply = teaApplicationService.selectById(id);
        int start = teaApply.getStart();
        int end = teaApply.getEnd();


        List<Integer> weeks = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            weeks.add(i);
        }

        // 使用生成的周数列表将相应的信息插入到lesson表中
        for (Integer week : weeks) {
            Lesson lesson = new Lesson();
            lesson.setSemesterName(teaApply.getSemesterName());
            lesson.setTeacherId(teaApply.getTeacherId());
            lesson.setName(teaApply.getName());
            lesson.setLabId(teaApplicationService.arrangeLabId(teaApply.getType(),teaApply.getNumber()));
            lesson.setClassName(teaApply.getClassName());
            lesson.setWeek(week);
            lesson.setSection(teaApply.getSection());
            lesson.setDayOfWeek(teaApply.getDayOfWeek());
            lessonMapper.insert(lesson);
        }

    }

    public void deleteById(Integer id) {
        lessonMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids){
            lessonMapper.deleteById(id);
        }
    }

    public void updateById(Lesson lesson) {
        lessonMapper.updateById(lesson);
    }

    public Lesson selectById(Integer id) {
        return lessonMapper.selectById(id);
    }

    public List<Lesson> selectAll(Lesson lesson) {

        return lessonMapper.selectAll(lesson);
    }

    public PageInfo<Lesson> selectPage(Lesson lesson, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Lesson> list = lessonMapper.selectAll(lesson);
        return PageInfo.of(list);
    }
}
