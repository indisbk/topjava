package ru.javawebinar.topjava.util;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static ru.javawebinar.topjava.service.MealServiceTest.*;

public class TimeRule implements TestRule {

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
                testSummary.put(name, testTime);
            }
        };
    }
}
