package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMealService userMealService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", userService.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        LoggedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model)
    {
        int userId = LoggedUser.id();
        model.addAttribute("mealList",
                UserMealsUtil.getWithExceeded(userMealService.getAll(userId), LoggedUser.getCaloriesPerDay()));
        return "mealList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addMeal(Model model)
    {
        final UserMeal meal = new UserMeal(LocalDateTime.now(), "", 1000);
        model.addAttribute("meal", meal);
        return "mealEdit";
    }

    @RequestMapping(value = "/update", params = {"id"}, method = RequestMethod.GET)
    public String updateMeal(Model model, @RequestParam(value = "id") int id)
    {
        int userId = LoggedUser.id();
        final UserMeal meal = userMealService.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealEdit";
    }

    @RequestMapping(value = "/delete", params = {"id"}, method = RequestMethod.GET)
    public String deleteMeal(Model model, @RequestParam(value = "id") int id)
    {
        int userId = LoggedUser.id();
        userMealService.delete(id, userId);
        model.addAttribute("mealList",
                UserMealsUtil.getWithExceeded(userMealService.getAll(userId), LoggedUser.getCaloriesPerDay()));
        return "mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST,
            params = {"id", "dateTime", "description", "calories"})
    public String setMeal(Model model,
                          @RequestParam(value = "id") String id,
                          @RequestParam(value = "dateTime") String dateTime,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "calories") int calories)
    {
        int userId = LoggedUser.id();
        final UserMeal userMeal = new UserMeal(LocalDateTime.parse(dateTime), description, calories);
        if (id.isEmpty())
        {
            userMealService.save(userMeal, userId);
        } else {
            userMeal.setId(Integer.parseInt(id));
            userMealService.update(userMeal, userId);
        }
        model.addAttribute("mealList",
                UserMealsUtil.getWithExceeded(userMealService.getAll(userId), LoggedUser.getCaloriesPerDay()));
        return "mealList";
    }

}
