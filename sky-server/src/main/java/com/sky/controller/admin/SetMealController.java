package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
