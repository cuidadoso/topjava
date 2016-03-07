package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * Created by Alejandro on 06.03.2016.
 */
public interface UserMealDao
{
    void create(UserMeal user);
    UserMeal read(long id);
    void update(UserMeal user);
    void delete(long id);
    List<UserMeal> list();
    Long newId();
}
