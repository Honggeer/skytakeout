package com.sky.mapper;

import com.sky.dto.SetmealDTO;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    List<Long> getSetMealIdsByDishIds(List<Long> dishIds);

    void insertBatch(List<SetmealDish> setMealDishes);

    void deleteByIds(List<Long> setMealIds);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getSetMealDishesById(Long id);
}
