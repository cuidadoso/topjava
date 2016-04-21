package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Alejandro on 17.04.2016.
 */
public class UserMealRestControllerTest extends AbstractControllerTest
{
    public static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Autowired
    UserMealService service;

    @Test
    public void testGetAll() throws Exception
    {
        List<UserMealWithExceed> expected = UserMealsUtil.getWithExceeded(USER_MEALS, UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(expected));
    }

    @Test
    public void testGet() throws Exception
    {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception
    {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertCollectionEquals(USER_MEALS_D, service.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception
    {
        UserMeal updated = MEAL1;
        updated.setCalories(700);
        updated.setDescription("Поздний завтрак");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testCreate() throws Exception
    {
        UserMeal expected = new UserMeal(LocalDateTime.now(), "Ранний обед", 200);
        List<UserMeal> expectedList = new ArrayList<>();

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        MATCHER.assertEquals(expected, returned);
        expectedList.add(expected);
        expectedList.addAll(USER_MEALS);
        MATCHER.assertCollectionEquals(expectedList, service.getAll(USER_ID));
    }
}
