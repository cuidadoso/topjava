package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE_ALL, query = "DELETE FROM UserMeal WHERE user.id = :user_id"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal WHERE id = :id AND user.id = :user_id"),
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT m FROM UserMeal m WHERE m.user.id = :user_id ORDER BY m.dateTime DESC "),
        @NamedQuery(name = UserMeal.MEAL, query = "SELECT m FROM UserMeal m WHERE m.user.id = :user_id AND m.id = :id"),
        @NamedQuery(name = UserMeal.BETWEEN, query = "SELECT m from UserMeal m WHERE m.user.id = :user_id AND m.dateTime > :startDate AND m.dateTime < :endDate ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.UPDATE, query = "UPDATE UserMeal m" +
                " SET m.description = :description, m.dateTime = :dateTime, m.calories = :calories" +
                " WHERE m.id = :id AND m.user.id = :user_id")

})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class UserMeal extends BaseEntity {

    public static final String DELETE_ALL = "UserMeal.delete.all";
    public static final String DELETE = "UserMeal.delete";
    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String MEAL = "UserMeal.getMeal";
    public static final String BETWEEN = "UserMeal.getBetween";
    public static final String UPDATE = "UserMeal.update";

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime dateTime;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @Column(name = "calories", columnDefinition = "default 1000")
    @Digits(fraction = 0, integer = 4)
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
