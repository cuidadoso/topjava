package ru.javawebinar.topjava.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Alejandro
 *         27.03.2016
 */
public class TestMealRule implements TestRule
{

    private static final Logger LOG = LoggerFactory.getLogger(TestMealRule.class);

    @Override
    public Statement apply(Statement base, Description description)
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                LocalDateTime start = LocalDateTime.now();
                base.evaluate();
                LOG.info(base.getClass().getSimpleName() + " DURATION: " + Duration.between(LocalDateTime.now(), start).getNano()/1000000 +"ms");

            }
        };
    }
}
