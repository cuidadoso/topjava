package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.MemoryHashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Alejandro on 06.03.2016.
 */
public class UserMealMemoryDaoImpl implements UserMealDao
{

    private static Map<Long, UserMeal> userMealsMap = MemoryHashMap.getUserMealsMap();

    @Override
    public void create(UserMeal userMeal)
    {
        userMealsMap.put(userMeal.getId(), userMeal);
    }

    @Override
    public UserMeal read(long id)
    {
        return userMealsMap.get(id);
    }

    @Override
    public void update(UserMeal userMeal)
    {
        userMealsMap.put(userMeal.getId(), userMeal);
    }

    @Override
    public void delete(long id)
    {
        userMealsMap.remove(id);
    }

    @Override
    public List<UserMeal> list()
    {
        return userMealsMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Long newId()
    {
        return userMealsMap.keySet().stream().max(Long::compare).get() + 1L;
    }
}
