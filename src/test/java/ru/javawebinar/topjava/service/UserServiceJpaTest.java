package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"jpa", "common-of-data-jpa"})
public class UserServiceJpaTest extends AbstractUserServiceTest {
}
