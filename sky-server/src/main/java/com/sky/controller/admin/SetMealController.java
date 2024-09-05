package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@ApiOperation("SetMeal related apis")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @PostMapping
    @ApiOperation("insert new setmeals")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("add new setmeals");
        if(setmealDTO.getName()==null||setmealDTO.getCategoryId()==null||setmealDTO.getPrice()==null||
                setmealDTO.getSetmealDishes().isEmpty() ||setmealDTO.getImage()==null)
            return Result.error("Please fill all the required information");
        setMealService.save(setmealDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("setMeal page search")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("SetMeal page search: {}",setmealPageQueryDTO);
        PageResult pageResult=setMealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    @ApiOperation("batch deletion of setmeals")
    public Result batchDelete(@RequestParam List<Long> ids){
        log.info("SetMeal batch deletion: {}",ids);
        setMealService.deleteBatch(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("get setMeal by id")
    public Result<SetmealDTO> getSetMealById(@PathVariable Long id){
        log.info("Get setMeal by id:{}",id);
        SetmealDTO setmealDTO=setMealService.getSetMealById(id);
        return Result.success(setmealDTO);
    }
    @PutMapping
    @ApiOperation("edit the setMeal info")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("SetMeal edition: {}",setmealDTO);
        setMealService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("abandon or active the setMeal")
    public Result status(@PathVariable Integer status, Long id){
        log.info("active or ban the setMeal: {}",status);
        setMealService.startOrStop(status,id);
        return Result.success();
    }

}
