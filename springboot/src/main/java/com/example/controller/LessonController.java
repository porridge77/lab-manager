package com.example.controller;

import com.example.common.Result;
import com.example.entity.Lesson;
import com.example.entity.StuReservation;
import com.example.service.LessonService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Resource
    private LessonService lessonService;

    @PostMapping("/add/{id}")
    public Result add(@PathVariable Integer id){
        lessonService.add(id);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        lessonService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        lessonService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateById(@RequestBody Lesson lesson) {
        lessonService.updateById(lesson);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Lesson lesson = lessonService.selectById(id);
        return Result.success(lesson);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Lesson lesson ) {
        List<Lesson> list = lessonService.selectAll(lesson);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Lesson lesson,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Lesson> page = lessonService.selectPage(lesson, pageNum, pageSize);
        return Result.success(page);
    }


}
