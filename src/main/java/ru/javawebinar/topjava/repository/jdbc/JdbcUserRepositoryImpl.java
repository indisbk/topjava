package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);


        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, user.getId());
                    Role role = user.getRoles().stream().findFirst().orElse(null);
                    ps.setString(2, role.equals(Role.ROLE_ADMIN) ? "ROLE_ADMIN" : "ROLE_USER");
                }

                @Override
                public int getBatchSize() {
                    return 1;
                }
            });
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day, r.role AS roles FROM users u LEFT JOIN user_roles r ON u.id = r.user_id WHERE id=?", new UsersWithRolesExtractor(), id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day, r.role AS roles FROM users u LEFT JOIN user_roles r ON u.id = r.user_id WHERE email=?", new UsersWithRolesExtractor(), email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        List<User> users = namedParameterJdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day, r.role AS roles FROM users u LEFT JOIN user_roles r ON u.id = r.user_id", new UsersWithRolesExtractor());
        users.sort(Comparator.comparing(User::getName).thenComparing(User::getEmail));
        return users;
    }

    private static final class UsersWithRolesExtractor implements ResultSetExtractor<List<User>> {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> map = new HashMap<>();
            User user = null;
            while (rs.next()) {
                var roles = new HashSet<Role>();
                Integer id = rs.getInt("id");
                user = map.get(id);
                if(user == null) {
                    user = new User();
                    user.setId(id);
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRegistered(rs.getDate("registered"));
                    user.setEnabled(rs.getBoolean("enabled"));
                    user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                    var role = rs.getString("roles");
                    switch (role) {
                        case "ROLE_ADMIN":
                            roles.add(Role.ROLE_ADMIN);
                            roles.add(Role.ROLE_USER);
                            break;
                        case "ROLE_USER":
                            roles.add(Role.ROLE_USER);
                            break;
                            default:
                                break;
                    }
                    user.setRoles(roles);
                    map.put(id, user);
                }

            }
            return new ArrayList<>(map.values());
        }
    }
}
