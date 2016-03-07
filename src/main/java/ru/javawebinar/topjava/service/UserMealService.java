package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * Created by Alejandro on 06.03.2016.
 */
public interface UserMealService
{
    void create(UserMeal userMeal);
    UserMeal read(long id);
    void update(UserMeal userMeal);
    void delete(long id);
    List<UserMeal> list();
    Long newId();
}
