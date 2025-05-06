package com.project.dao.impl;

import com.project.dao.interfaceDao.UserDao;
import com.project.entity.UserEntity;
import com.project.exception.UserDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

public class UserImplDao implements UserDao {

    private static volatile UserImplDao INSTANCE;
    private static final RoleImplDao roleDao = RoleImplDao.getInstance();
    private static final String ROLE_ID = "role_id";
    private static final String ROLE_NAME = "role_name";
    private static final String USER_ID = "user_id";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_AGE = "age";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PASSPORT_NO = "passport_number";


    private static final String FIND_USER_BY_ID_SQL = """
            SELECT *
            FROM users u
            JOIN roles r
            ON u.role_id = r.role_id
            WHERE u.user_id = ?
            """;

    private static final String FIND_ALL_USERS_SQL = """
            SELECT *
            FROM users u
            JOIN roles r
            ON u.role_id = r.role_id  
            """;

    private static final String CREATE_USER_SQL = """
            INSERT INTO users (first_name, last_name, age, password, passport_number, role_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_USER_SQL = """
            UPDATE users u
            SET first_name = ?,
            last_name = ?,
            age = ?,
            password = ?,
            passport_number = ?,
            role_id = ?
            WHERE u.user_id = ?
            """;

    private static final String DELETE_USER_SQL = """
            DELETE
            FROM users u
            WHERE u.user_id = ?
            """;

    private UserImplDao() {
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

    @Override
    public Optional<UserEntity> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet));
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
                list.add(buildUser(resultSet));
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }

    private static UserEntity buildUser(ResultSet resultSet) throws SQLException {
        return new UserEntity(
                resultSet.getLong(USER_ID),
                resultSet.getString(USER_FIRST_NAME),
                resultSet.getString(USER_LAST_NAME),
                resultSet.getInt(USER_AGE),
                resultSet.getString(USER_PASSWORD),
                resultSet.getString(USER_PASSPORT_NO),
                roleDao.findById(resultSet.getInt(ROLE_ID)).get()
        );
    }

    @Override
    public UserEntity save(UserEntity user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPassportNumber());
            preparedStatement.setInt(6, user.getRole().getRoleId());
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
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPassportNumber());
            preparedStatement.setInt(6, user.getRole().getRoleId());
            preparedStatement.setLong(7, user.getUserId());
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
            preparedStatement.setLong(1, id);
           return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() user: " + e.getMessage());
            throw new UserDaoException(e);
        }
    }
}
