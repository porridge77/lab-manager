package com.example.controller;

import com.example.common.Result;
import com.example.entity.TeaApplication;
import com.example.service.TeaApplicationService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/teaapplication")
public class TeaApplicationController {
    @Resource
    private TeaApplicationService teaApplicationService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody TeaApplication apply) {
        teaApplicationService.add(apply);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        teaApplicationService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        teaApplicationService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody TeaApplication apply) {
        teaApplicationService.updateById(apply);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        TeaApplication apply = teaApplicationService.selectById(id);
        return Result.success(apply);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(TeaApplication apply ) {
        List<TeaApplication> list = teaApplicationService.selectAll(apply);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(TeaApplication apply,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<TeaApplication> page = teaApplicationService.selectPage(apply, pageNum, pageSize);
        return Result.success(page);

    }

    @GetMapping("/arrangeLabId")
    public Result arrangeLabId(String type, Integer number){
        Integer labId = teaApplicationService.arrangeLabId(type, number);
        return Result.success(labId);
    }


}
