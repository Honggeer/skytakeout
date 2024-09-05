package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmeal.setStatus(0);
        setmealMapper.insert(setmeal);
        Long setMealId=setmeal.getId();
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        if(setmealDishes!=null&&setmealDishes.size()>0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setMealId);
            });
            setMealDishMapper.insertBatch(setmealDishes);
        }

    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page=setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for(Long id:ids){
            Setmeal setmeal=setmealMapper.searchById(id);
            if(setmeal.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        setmealMapper.deleteByIds(ids);
        setMealDishMapper.deleteByIds(ids);
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        List<Long> ids = new ArrayList<>();
        ids.add(setmeal.getId());
        setMealDishMapper.deleteByIds(ids);
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        if(setmealDishes!=null&&setmealDishes.size()>0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
            });
            setMealDishMapper.insertBatch(setmealDishes);

        }

    }

    @Override
    public SetmealDTO getSetMealById(Long id) {
        Setmeal setmeal=setmealMapper.searchById(id);
        SetmealDTO setmealDTO=new SetmealDTO();
        BeanUtils.copyProperties(setmeal,setmealDTO);
        List<SetmealDish> setmealDishes=setMealDishMapper.getSetMealDishesById(id);
        setmealDTO.setSetmealDishes(setmealDishes);
        return setmealDTO;
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal=new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }
}
