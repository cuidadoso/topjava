package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alejandro
 *         03.04.2016
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer>
{

    @Transactional
    @Modifying
    @Query(name = UserMeal.DELETE)
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal userMeal);

    @Query(name = UserMeal.GET)
    UserMeal getMeal(@Param("id") int id, @Param("userId") int userId);

    List<UserMeal> getByUserIdOrderByDateTimeDesc(int userId);

    @Query(name = UserMeal.GET_BETWEEN)
    List<UserMeal> getBetween(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate,
                              @Param("userId") int userId);

}
