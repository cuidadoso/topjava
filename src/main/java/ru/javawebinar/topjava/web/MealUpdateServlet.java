package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Alejandro on 07.03.2016.
 */
public class MealUpdateServlet extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(MealUpdateServlet.class);
    private UserMealService userMealService = new UserMealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LOG.debug("redirect to mealUpdate");
        long id = Long.valueOf(req.getParameter("id"));
        String description = req.getParameter("description");
        int calories = Integer.valueOf(req.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        UserMeal userMeal = new UserMeal(id, dateTime, description, calories);
        userMealService.update(userMeal);
        resp.sendRedirect("meals");
    }
}
