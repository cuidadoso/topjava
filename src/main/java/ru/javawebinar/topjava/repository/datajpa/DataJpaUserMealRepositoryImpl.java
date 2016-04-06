package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
@Profile(Profiles.DATAJPA)
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{

    private static final Sort SORT_DATE = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private ProxyUserMealRepository mealProxy;
    @Autowired
    private ProxyUserRepository userProxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        userMeal.setUser(userProxy.findOne(userId));
        if (userMeal.isNew()) {
            mealProxy.save(userMeal);
            return userMeal;
        } else {
            return get(userMeal.getId(), userId) == null ? null : mealProxy.save(userMeal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealProxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return mealProxy.getMeal(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return mealProxy.getByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return mealProxy.getBetween(startDate, endDate, userId);
    }
}
