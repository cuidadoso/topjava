package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {

    UserMeal save(UserMeal userMeal);

    void delete(int id) throws NotFoundException;

    UserMeal get(int id) throws NotFoundException;

    void update(UserMeal userMeal);

    List<UserMeal> getAll(int userId) throws NotFoundException;

    List<UserMeal> getAll(int userId, LocalDateTime startDate, LocalDateTime endDate) throws NotFoundException;
}
