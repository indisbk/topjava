package ru.javawebinar.topjava.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile("hsqldb")
public class JdbcForHsqldb extends AbstractJdbcMealRepository {
    @Autowired
    public JdbcForHsqldb(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected MapSqlParameterSource createMap(Meal meal, int userId) {
        var tm = Timestamp.valueOf(meal.getDateTime());
        return new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", tm)
                .addValue("user_id", userId);
    }

    @Override
    protected Timestamp getValue(LocalDateTime date) {
        return Timestamp.valueOf(date);
    }
}
