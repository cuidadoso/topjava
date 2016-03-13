package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil
{
    public static final List<User> USER_LIST = Arrays.asList(
            new User(null, "Alejandro", "alejandro@mail.com", "alejandro", Role.ROLE_USER, Role.ROLE_USER),
            new User(null, "Diego", "diego@mail.com", "Diego", Role.ROLE_USER, Role.ROLE_USER),
            new User(null, "Martin", "martin@mail.com", "martin", Role.ROLE_ADMIN, Role.ROLE_ADMIN),
            new User(null, "Carlos", "carlos@mail.com", "carlos", Role.ROLE_USER, Role.ROLE_USER),
            new User(null, "Francisco", "francisco@mail.com", "francisco", Role.ROLE_USER, Role.ROLE_USER)
    );

    public static final List<UserMeal> MEAL_LIST = Arrays.asList(
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, USER_LIST.get(0)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, USER_LIST.get(0)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, USER_LIST.get(0)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, USER_LIST.get(0)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, USER_LIST.get(0)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, USER_LIST.get(0)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 502, USER_LIST.get(2)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1002, USER_LIST.get(2)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 502, USER_LIST.get(2)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1002, USER_LIST.get(2)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 502, USER_LIST.get(2)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 512, USER_LIST.get(2)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 501, USER_LIST.get(1)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1001, USER_LIST.get(1)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 501, USER_LIST.get(1)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1001, USER_LIST.get(1)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 501, USER_LIST.get(1)),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 511, USER_LIST.get(1))
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<UserMealWithExceed> filteredMealsWithExceeded = getFilteredWithExceeded(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY);
        filteredMealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }

    public static List<UserMealWithExceed> getWithExceeded(Collection<UserMeal> mealList, int caloriesPerDay) {
        return getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        mealList.forEach(meal -> caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        final List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        mealList.forEach(meal -> {
            final LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                mealExceeded.add(createWithExceed(meal, caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        });
        return mealExceeded;
    }

    public static UserMealWithExceed createWithExceed(UserMeal um, boolean exceeded) {
        return new UserMealWithExceed(um.getId(), um.getDateTime(), um.getDescription(), um.getCalories(), exceeded);
    }
}
