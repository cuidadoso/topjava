package ru.javawebinar.topjava.web.meal;

import org.junit.Test;

import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by Alejandro on 17.04.2016.
 */
public class UserMealControllerTest extends AbstractControllerTest
{
    @Test
    public void testMealList() throws Exception
    {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", hasSize(6)))
                .andExpect(model().attribute("mealList", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ + 2)),
                                hasProperty("description", is("Завтрак")),
                                hasProperty("calories", is(500))
                        )
                )))
                .andExpect(model().attribute("mealList", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ + 3)),
                                hasProperty("description", is("Обед")),
                                hasProperty("calories", is(1000))
                        )
                )));
    }

    @Test
    public void testEditForCreate() throws Exception
    {
        mockMvc.perform(get("/meals/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealEdit"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealEdit.jsp"))
                .andExpect(model().attribute("meal",
                        hasProperty("id", nullValue())))
                .andExpect(model().attribute("meal",
                        hasProperty("description", isEmptyString())))
                .andExpect(model().attribute("meal",
                        hasProperty("calories", is(1000))));
    }
}