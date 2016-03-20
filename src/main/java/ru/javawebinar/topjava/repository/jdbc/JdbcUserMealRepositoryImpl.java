package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> USER_MEAL_ROW_MAPPER = (rs, rowNum) ->
            new UserMeal(rs.getInt("id"),
                         rs.getTimestamp("date_time").toLocalDateTime(),
                         rs.getString("description"),
                         rs.getInt("calories"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("userId", LoggedUser.id())
                .addValue("dateTime", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());

        if (userMeal.isNew())
        {
            Number newKey = insertMeal.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        }
        else
        {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET user_id=:userId, date_time=:dateTime, description=:description, " +
                            "calories=:calories WHERE id=:id", map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND meals.user_id=?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> userMeals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", USER_MEAL_ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(userMeals);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", USER_MEAL_ROW_MAPPER, userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Timestamp start = Timestamp.valueOf(startDate);
        Timestamp end = Timestamp.valueOf(endDate);
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? AND date_time > ? AND date_time < ? ORDER BY date_time", USER_MEAL_ROW_MAPPER, userId, start, end);
    }
}
