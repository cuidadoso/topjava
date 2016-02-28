package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil
{
    public static void main(String[] args)
    {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {
        List<UserMealWithExceed> result = new ArrayList<>();

        mealList.stream()
            .filter(userMeal ->
                    userMeal.getDateTime().toLocalTime().isAfter(startTime) &&
                            userMeal.getDateTime().toLocalTime().isBefore(endTime)
            ).collect(Collectors.toList())
                .stream()
                .forEach(userMeal ->
                {
                    LocalDateTime startOfDay = userMeal.getDateTime().toLocalDate().atStartOfDay();
                    LocalDateTime endOfDay = userMeal.getDateTime().toLocalDate().atStartOfDay().plusDays(1).minusSeconds(1);
                    int caloriesSum = mealList.stream()
                            .filter(userMeal1 -> userMeal1.getDateTime().isAfter(startOfDay) &&
                                    userMeal1.getDateTime().isBefore(endOfDay))
                            .mapToInt(UserMeal::getCalories)
                            .sum();
                    boolean exceed = caloriesSum > caloriesPerDay;
                    result.add(new UserMealWithExceed(userMeal.getDateTime(),
                            userMeal.getDescription(), userMeal.getCalories(), exceed));
                });

        return result;
    }
}
