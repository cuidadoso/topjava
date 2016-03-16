package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Alejandro
 * 13.03.2016
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository
{
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.USER_LIST.stream().forEach(this::save);
    }

    @Override
    public User save(User user)
    {
        if(user.isNew())
        {
            user.setId(counter.incrementAndGet());
        }
        LOG.info("save " + user);
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id)
    {
        LOG.info("delete " + id);
        if(repository.containsKey(id))
        {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User get(int id)
    {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email)
    {
        LOG.info("getByEmail " + email);
        return repository.values().stream().filter(user -> email.equals(user.getEmail())).findFirst().get();
    }

    @Override
    public List<User> getAll()
    {
        LOG.info("getAll");
        List<User> sortedUsers = repository.values().stream().collect(Collectors.toList());
        Collections.sort(sortedUsers, (user1, user2) -> user1.getName().compareToIgnoreCase(user2.getName()));
        return sortedUsers;
    }
}
