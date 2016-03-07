package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.UserMealDao;
import ru.javawebinar.topjava.dao.UserMealMemoryDaoImpl;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * Created by Alejandro on 06.03.2016.
 */
public class UserMealServiceImpl implements UserMealService
{
    private UserMealDao userMealDao = new UserMealMemoryDaoImpl();

    @Override
    public void create(UserMeal userMeal)
    {
        userMealDao.create(userMeal);
    }

    @Override
    public UserMeal read(long id)
    {
        return userMealDao.read(id);
    }

    @Override
    public void update(UserMeal userMeal)
    {
        userMealDao.update(userMeal);
    }

    @Override
    public void delete(long id)
    {
        userMealDao.delete(id);
    }

    @Override
    public List<UserMeal> list()
    {
        return userMealDao.list();
    }

    @Override
    public Long newId()
    {
        return userMealDao.newId();
    }
}
