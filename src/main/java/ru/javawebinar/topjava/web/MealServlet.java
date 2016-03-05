package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Alejandro on 05.03.2016.
 */
public class MealServlet extends HttpServlet
{
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LOG.debug("redirect to mealList");

        List<UserMealWithExceed> list = UserMealsUtil.getFilteredMeals();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/mealList.jsp").forward(req, resp);
        //resp.sendRedirect("mealList.jsp");
    }
}
