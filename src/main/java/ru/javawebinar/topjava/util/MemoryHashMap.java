package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alejandro on 07.03.2016.
 */
public class MemoryHashMap
{

    private static Map<Long, UserMeal> userMealsMap = new HashMap<>();
    static
    {
        Arrays.asList(
                new UserMeal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510))
        .stream()
                .forEach(userMeal -> userMealsMap.put(userMeal.getId(), userMeal));
    }

    private MemoryHashMap()
    {
    }

    public static Map<Long, UserMeal> getUserMealsMap()
    {
        return userMealsMap;
    }
}
