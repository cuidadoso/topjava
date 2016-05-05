package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.UserTo;

/**
 * GKislin
 */
public class UserUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static User createFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), Role.ROLE_USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static UserMeal createFromTo(MealTo newMeal) {
        new UserMeal(null, newMeal.getDateTime(), newMeal.getDescription(), newMeal.getCalories());

        return null;
    }

    public static UserMeal updateFromTo(UserMeal userMeal, MealTo mealTo) {
        userMeal.setDateTime(mealTo.getDateTime());
        userMeal.setDescription(mealTo.getDescription());
        userMeal.setCalories(mealTo.getCalories());
        return userMeal;
    }
}
