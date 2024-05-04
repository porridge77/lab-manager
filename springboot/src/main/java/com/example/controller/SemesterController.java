package com.example.controller;

import com.example.common.Result;
import com.example.entity.Semester;
import com.example.service.SemesterService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学期信息前端操作接口
 */
@RestController
@RequestMapping("semester")
public class SemesterController {
    @Resource
    private SemesterService semesterService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Semester semester) {
        semesterService.add(semester);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        semesterService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        semesterService.deleteBatch(ids);
        return Result.success();
    }



    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Semester semester = semesterService.selectById(id);
        return Result.success(semester);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Semester semester){
        List<Semester> list = semesterService.selectAll(semester);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Semester semester,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Semester> page = semesterService.selectPage(semester, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 设置当前学期
     */
    @PutMapping("/setCurrent/{id}")
    public Result setCurrentSemester(@PathVariable Integer id) {
        semesterService.setCurrentSemester(id);
        return Result.success();
    }

    /**
     * 获取当前学期
     */
    @GetMapping("/current")
    public Result getCurrentSemester() {
        String currentSemesterName = semesterService.getCurrentSemester();
        return Result.success(currentSemesterName);
    }
}
