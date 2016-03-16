package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private UserMealRestController userMealRestController;
    private ProfileRestController profileRestController;
    ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        userMealRestController = appCtx.getBean(UserMealRestController.class);
        profileRestController = appCtx.getBean(ProfileRestController.class);
    }

    @Override
    public void destroy()
    {
        super.destroy();
        appCtx.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Integer idd = id.isEmpty() ? null : Integer.valueOf(id);
        UserMeal userMeal = new UserMeal(idd,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")), profileRestController.get().getId());
        boolean newUserMeal = userMeal.isNew();
        LOG.info(newUserMeal ? "Create {}" : "Update {}", userMeal);
        if(newUserMeal)
        {
            userMealRestController.create(userMeal);
        }
        else
        {
            userMealRestController.update(userMeal, idd);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String startDate = request.getParameter("startDate") + "T" + UserMealsUtil.DEFAULT_START_TIME;
        String endDate = request.getParameter("endDate") + "T" + UserMealsUtil.DEFAULT_END_TIME;

        if (action == null) {
            int id = profileRestController.get().getId();
            if(startTime != null && endTime != null && !"".equals(startTime) && !"".equals(endTime))
            {
                LOG.info("getFiltered");
                LocalDateTime startD = LocalDateTime.parse(startDate);
                LocalDateTime endD = LocalDateTime.parse(endDate);
                request.setAttribute("mealList", userMealRestController.getAll(id, startD, endD,
                                LocalTime.parse(startTime), LocalTime.parse(endTime)));
                request.setAttribute("startDate", startD.toLocalDate());
                request.setAttribute("endDate", endD.toLocalDate());
                request.setAttribute("startTime", LocalTime.parse(startTime));
                request.setAttribute("endTime", LocalTime.parse(endTime));

            }
            else
            {
                LOG.info("getAll");
                request.setAttribute("mealList", userMealRestController.getAll(id));
                request.setAttribute("startDate", UserMealsUtil.DEFAULT_START_DATE.toLocalDate());
                request.setAttribute("endDate", UserMealsUtil.DEFAULT_END_DATE.toLocalDate());
                request.setAttribute("startTime", UserMealsUtil.DEFAULT_START_TIME);
                request.setAttribute("endTime", UserMealsUtil.DEFAULT_END_TIME);
            }
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            userMealRestController.delete(id);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000, profileRestController.get().getId()) :
                    userMealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
