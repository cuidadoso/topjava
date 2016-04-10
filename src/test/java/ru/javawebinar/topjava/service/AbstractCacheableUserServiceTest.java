package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

/**
 * @author Alejandro
 *         10.04.2016
 */
public abstract class AbstractCacheableUserServiceTest extends AbstractUserServiceTest
{
    @Autowired
    protected JpaUtil jpaUtil;

    @Override
    @Before
    public void setUp() throws Exception {
        service.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }

}
