package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest
{

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception
    {
        dbPopulator.execute();
        resetMEALS();
    }

    @Test
    public void testGet() throws Exception
    {
        UserMeal userMeal = service.get(FIRST_MEAL_ID, USER_ID);
        MATCHER.assertEquals(MEAL_1, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGet() throws Exception
    {
        service.get(FIRST_MEAL_ID, ADMIN_ID);
    }


    @Test
    public void testDelete() throws Exception
    {
        UserMeal userMeal = service.get(FIRST_MEAL_ID, USER_ID);
        service.delete(FIRST_MEAL_ID, USER_ID);
        Collection<UserMeal> toCheck = getMEALS();
        toCheck.remove(userMeal);
        MATCHER.assertCollectionEquals(toCheck, service.getAll(USER_ID));

    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(FIRST_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception
    {
        LocalDate start =  LocalDate.of(2015, Month.MAY, 20);
        LocalDate end =  LocalDate.of(2015, Month.MAY, 30);
        Collection<UserMeal> meals = service.getBetweenDates(start, end, USER_ID);
        Collection<UserMeal> toCheck = getMEALS();
        toCheck.remove(MEAL_4);
        toCheck.remove(MEAL_5);
        toCheck.remove(MEAL_6);
        MATCHER.assertCollectionEquals(toCheck, meals);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGetBetweenDates() throws Exception
    {
        LocalDate start =  LocalDate.of(2015, Month.MAY, 20);
        LocalDate end =  LocalDate.of(2015, Month.MAY, 30);
        service.getBetweenDates(start, end, ADMIN_ID);
    }

    /**
     * Result of method getBetweenDateTimes is not for view. It processed in controller then for view.
     * @throws Exception
     */
    @Test
    public void testGetBetweenDateTimes() throws Exception
    {
        LocalDateTime start =  LocalDateTime.of(2015, Month.MAY, 20, 10, 0);
        LocalDateTime end =  LocalDateTime.of(2015, Month.MAY, 31, 15, 0);
        Collection<UserMeal> toCheck = getMEALS();
        toCheck.remove(MEAL_6);
        Collection<UserMeal> meals = service.getBetweenDateTimes(start, end, USER_ID);
        MATCHER.assertCollectionEquals(toCheck, meals);
    }

    @Test
    public void testGetAll() throws Exception
    {
        Collection<UserMeal> meals = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(getMEALS(), meals);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGetAll() throws Exception
    {
        service.getAll(ADMIN_ID);
    }

    @Test
    public void testUpdate() throws Exception
    {
        UserMeal userMeal = service.get(FIRST_MEAL_ID, USER_ID);
        userMeal.setDescription("New description");
        userMeal.setCalories(2222);
        service.update(userMeal, USER_ID);
        MATCHER.assertEquals(userMeal, service.get(FIRST_MEAL_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception
    {
        UserMeal userMeal = service.get(FIRST_MEAL_ID, USER_ID);
        userMeal.setDescription("New description 2");
        userMeal.setCalories(4444);
        service.update(userMeal, ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception
    {
        UserMeal userMeal = new UserMeal(null, LocalDateTime.now(), "New meal", 1111);
        UserMeal createdUserMeal = service.save(userMeal, USER_ID);
        Collection<UserMeal> toCheck = getMEALS();
        toCheck.add(createdUserMeal);
        MATCHER.assertCollectionEquals(toCheck.stream()
                .sorted((o1, o2) -> o1.getDateTime().isBefore(o2.getDateTime()) ? 1 : -1).collect(Collectors.toList()),
                service.getAll(USER_ID));
    }
}