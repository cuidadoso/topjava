package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        if(userMeal.isNew())
        {
            userMeal.setUser(em.getReference(User.class, userId));
            em.persist(userMeal);
        }
        else
        {
            if(em.createNamedQuery(UserMeal.UPDATE)
                    .setParameter("description", userMeal.getDescription())
                    .setParameter("dateTime", userMeal.getDateTime())
                    .setParameter("calories", userMeal.getCalories())
                    .setParameter("id", userMeal.getId())
                    .setParameter("user_id", userId)
                    .executeUpdate() == 0)
            {
                return null;
            }
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal result;
        try
        {
            result =  em.createNamedQuery(UserMeal.MEAL, UserMeal.class)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
        return result;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.BETWEEN, UserMeal.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("user_id", userId)
                .getResultList();
    }
}