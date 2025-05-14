package com.project.dao.impl;

import com.project.dao.interfaceDao.UserDao;
import com.project.entity.Role;
import com.project.entity.UserEntity;
import com.project.exception.UserDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

import static java.sql.Statement.*;

public class UserImplDao implements UserDao {

    private static volatile UserImplDao INSTANCE;
//    private static final RoleImplDao roleDao = RoleImplDao.getInstance();
//    private static final String ROLE_ID = "role_id";
//    private static final String ROLE_NAME = "role_name";
    private static final String USER_ID = "user_id";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_AGE = "age";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "email";
    private static final String USER_ROLE = "role";
//    private static final String USER_PASSPORT_NO = "passport_number";


//    private static final String FIND_USER_BY_ID_SQL = """
//            SELECT *
//            FROM users u
//            JOIN roles r
//            ON u.role_id = r.role_id
//            WHERE u.user_id = ?
//            """;

private static final String FIND_BY_EMAIL_AND_PASSWORD_SQL = """
        SELECT *
        FROM users u
        WHERE u.email = ?
        AND u.password = ?
        """;

    private static final String FIND_USER_BY_ID_SQL = """
            SELECT *
            FROM users u
            WHERE u.user_id = ?
            """;

//    private static final String FIND_ALL_USERS_SQL = """
//            SELECT *
//            FROM users u
//            JOIN roles r
//            ON u.role_id = r.role_id
//            """;

private static final String FIND_ALL_USERS_SQL = """
            SELECT *
            FROM users
            """;

    private static final String CREATE_USER_SQL = """
            INSERT INTO users (first_name, last_name, age, email, password, role)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_USER_SQL = """
            UPDATE users u
            SET first_name = ?,
            last_name = ?,
            age = ?,
            email = ?,
            password = ?,
            role = ?
            WHERE u.user_id = ?
            """;

    private static final String DELETE_USER_SQL = """
            DELETE
            FROM users u
            WHERE u.user_id = ?
            """;

    private UserImplDao() {
    }

    public Optional<UserEntity> findByEmailAndPassword(String email, String password) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserEntity userEntity = null;
            if (resultSet.next()) {
                userEntity = buildUserEntity(resultSet);
            }
            return Optional.ofNullable(userEntity);
        } catch (SQLException e) {
            System.err.println("Oшибка при findByEmailAndPassword() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUserEntity(resultSet));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.err.println("Oшибка при findById() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<UserEntity> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(buildUserEntity(resultSet));
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }

    @Override
    public UserEntity save(UserEntity user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, user.getFirstName());
            preparedStatement.setObject(2, user.getLastName());
            preparedStatement.setObject(3, user.getAge());
            preparedStatement.setObject(4, user.getEmail());
            preparedStatement.setObject(5, user.getPassword());
            preparedStatement.setObject(6, user.getRole().name());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getLong(USER_ID));
            }
            return user;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(UserEntity user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
            preparedStatement.setObject(1, user.getFirstName());
            preparedStatement.setObject(2, user.getLastName());
            preparedStatement.setObject(3, user.getAge());
            preparedStatement.setObject(5, user.getEmail());
            preparedStatement.setObject(4, user.getPassword());
            preparedStatement.setObject(6, user.getRole().name());
            preparedStatement.setObject(7, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setObject(1, id);
           return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }

    private static UserEntity buildUserEntity(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .userId(resultSet.getObject(USER_ID, Long.class))
                .firstName(resultSet.getObject(USER_FIRST_NAME, String.class))
                .lastName(resultSet.getObject(USER_LAST_NAME, String.class))
                .age(resultSet.getObject(USER_AGE, Integer.class))
                .email(resultSet.getObject(USER_EMAIL, String.class))
                .password(resultSet.getObject(USER_PASSWORD, String.class))
                .role(Role.find(resultSet.getObject(USER_ROLE, String.class)).orElse(null))
                .build();
//                roleDao.findById(resultSet.getInt(ROLE_ID)).get()
    }

    public static UserImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (UserImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserImplDao();
                }
            }
        }
        return INSTANCE;
    }
}
