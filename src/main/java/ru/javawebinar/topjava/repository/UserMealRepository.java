package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {

    UserMeal save(UserMeal userMeal);

    // false if not found
    boolean delete(int id);

    // null if not found
    UserMeal get(int id);

    // null if not found
    List<UserMeal> getAll(int userId);

    // null if not found
    List<UserMeal> getAll(int userId, LocalDateTime startDate, LocalDateTime endDate);
}
