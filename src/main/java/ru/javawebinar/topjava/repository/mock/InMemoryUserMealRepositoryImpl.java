package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.info("save " + userMeal);
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        if(repository.containsKey(id))
        {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public UserMeal get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        LOG.info("getAll");
        List<UserMeal> sortedUserMeals = new ArrayList<>();
        repository.values()
                .stream()
                .filter(userMeal -> userMeal.isUserBelong(userId))
                .forEach(userMeal -> sortedUserMeals.add(userMeal));
        Collections.sort(sortedUserMeals,
                (userMeal1, userMeal2) -> userMeal1.getDateTime().isBefore(userMeal2.getDateTime()) ? -1 : 1);
        return sortedUserMeals.isEmpty() ? null : sortedUserMeals;
    }
}

