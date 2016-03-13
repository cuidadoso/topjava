package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal extends BaseEntity
{

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final User user;

    public UserMeal(LocalDateTime dateTime, String description, int calories, User user) {
        this(null, dateTime, description, calories, user);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories, User user) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.user = user;
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

    public Integer getId() {
        return id;
    }

    public boolean isUserBelong(int userId)
    {
        return user.getId() == userId;
    }

    @Override
    public String toString()
    {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", user=" + user.getId() + ":" + user.getName() +
                '}';
    }
}
