package ru.javawebinar.topjava.util;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.javawebinar.topjava.service.MealServiceTest.*;

public class TimeRule implements TestRule {
    private static final Logger log = LoggerFactory.getLogger(TimeRule.class);

    private String name;

    private Long testTime;

    @Override
    public Statement apply(Statement base, Description description) {
        name = description.getMethodName();
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long before = System.nanoTime();
                base.evaluate();
                long after = System.nanoTime();
                testTime = (after - before)/1000000;
                log.info("************INFO ABOUT TIME************");
                log.info("Time of test: {}", testTime);
                log.info("************INFO ABOUT TIME************");
                testSummary.put(name, testTime);

            }
        };
    }
}
