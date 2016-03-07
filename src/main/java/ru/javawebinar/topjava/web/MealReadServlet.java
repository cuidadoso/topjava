package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 07.03.2016.
 */
public class MealReadServlet extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(MealReadServlet.class);
    private UserMealService userMealService = new UserMealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LOG.debug("redirect to mealRead");
        List<UserMeal> mealList = new ArrayList();
        mealList.add(userMealService.read(Long.valueOf(req.getParameter("id"))));
        List<UserMealWithExceed> list =  UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        req.setAttribute("meal", list.get(0));
        //req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/mealRead.jsp").forward(req, resp);

    }
}
