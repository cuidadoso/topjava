package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);

    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll(int userId)
    {
        LOG.info("getAll");
        return UserMealsUtil.getWithExceeded(service.getAll(userId), LoggedUser.getCaloriesPerDay());
    }

    public List<UserMealWithExceed> getAll(int userId, LocalDateTime startDate, LocalDateTime endDate, LocalTime startTime, LocalTime endTime)
    {
        LOG.info("getAllByDate");
        return UserMealsUtil.getFilteredWithExceeded(service.getAll(userId, startDate, endDate),
                startTime, endTime, LoggedUser.getCaloriesPerDay());
    }

    public UserMeal get(int id)
    {
        LOG.info("get " + id);
        return service.get(id);
    }

    public UserMeal create(UserMeal userMeal)
    {
        userMeal.setId(null);
        LOG.info("create " +userMeal);
        return service.save(userMeal);
    }

    public void delete(int id)
    {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void update(UserMeal userMeal, int id)
    {
        userMeal.setId(id);
        LOG.info("update " + userMeal);
        service.update(userMeal);
    }
}
