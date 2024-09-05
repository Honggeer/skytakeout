package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "dished related api")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("Add dish")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("added dish: {}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("dishes page search")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("Dishes search: {}",dishPageQueryDTO);
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("batch deletion")
    public Result delete(@RequestParam List<Long> ids){
        log.info("dishes batch deletion: {}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("search dishes by id")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("Search dishes by id: {}",id);
        DishVO dishVO=dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    @PutMapping
    @ApiOperation("edit dish info")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("Edit dish info: {}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("get dishes by category Id")
    public Result<List<Dish>> getDishesByCategoryId(@RequestParam Long categoryId){
        log.info("Get dished by category Id: {}",categoryId);
        List<Dish> dishes=dishService.getDishesByCategoryId(categoryId);
        return Result.success(dishes);
    }
    @PostMapping("status/{status}")
    @ApiOperation("change the status of the dish")
    public Result status(@PathVariable Integer status,Long id){
        log.info("change the status of the dish");
        dishService.startOrStop(status,id);
        return Result.success();
    }


}
