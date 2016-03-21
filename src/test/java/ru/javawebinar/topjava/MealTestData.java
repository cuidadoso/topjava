package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int FIRST_MEAL_ID = START_SEQ + 2;

    public static final UserMeal MEAL_1 = new UserMeal(FIRST_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final UserMeal MEAL_2 = new UserMeal(FIRST_MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final UserMeal MEAL_3 = new UserMeal(FIRST_MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final UserMeal MEAL_4 = new UserMeal(FIRST_MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final UserMeal MEAL_5 = new UserMeal(FIRST_MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final UserMeal MEAL_6 = new UserMeal(FIRST_MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final UserMeal MEAL_7 = new UserMeal(FIRST_MEAL_ID + 6, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 400);
    public static final UserMeal MEAL_8 = new UserMeal(FIRST_MEAL_ID + 7, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final UserMeal MEAL_9 = new UserMeal(FIRST_MEAL_ID + 8, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 400);
    public static final UserMeal MEAL_10 = new UserMeal(FIRST_MEAL_ID + 9, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final UserMeal MEAL_11 = new UserMeal(FIRST_MEAL_ID + 10, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 400);
    public static final UserMeal MEAL_12 = new UserMeal(FIRST_MEAL_ID + 11, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 410);

    private static Collection<UserMeal> MEALS = new ArrayList<>();

    public static void resetMEALS()
    {
        MEALS.clear();
        MEALS.add(MEAL_1);
        MEALS.add(MEAL_2);
        MEALS.add(MEAL_3);
        MEALS.add(MEAL_4);
        MEALS.add(MEAL_5);
        MEALS.add(MEAL_6);
    }

    public static Collection<UserMeal> getMEALS()
    {
        return MEALS.stream()
                .sorted((o1, o2) -> o1.getDateTime().isBefore(o2.getDateTime()) ? 1 : -1).collect(Collectors.toList());
    }
}
