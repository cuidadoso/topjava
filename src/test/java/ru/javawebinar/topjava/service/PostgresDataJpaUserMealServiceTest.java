package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * @author Alejandro
 *         06.04.2016
 */
@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class PostgresDataJpaUserMealServiceTest extends UserMealServiceTest
{
}
