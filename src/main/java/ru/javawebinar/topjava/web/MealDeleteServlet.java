package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alejandro on 07.03.2016.
 */
public class MealDeleteServlet extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(MealDeleteServlet.class);
    private UserMealService userMealService = new UserMealServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LOG.debug("redirect to mealDelete");
        long id = Long.valueOf(req.getParameter("id"));
        userMealService.delete(id);
        resp.sendRedirect("meals");
    }
}
