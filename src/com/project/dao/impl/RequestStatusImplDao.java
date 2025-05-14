package com.project.dao.impl;

import com.project.dao.interfaceDao.RequestStatusDao;
import com.project.entity.RequestStatusEntity;
import com.project.exception.RequestStatusDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

import static java.sql.Statement.*;

public class RequestStatusImplDao implements RequestStatusDao {

    private static volatile RequestStatusImplDao INSTANCE;
    private static final String STATUS_ID = "status_id";
    private static final String STATUS_NAME = "status_name";

    private static final String FIND_REQUEST_STATUS_BY_ID_SQL = """
            SELECT *
            FROM request_status rs
            WHERE rs.status_id = ?
            """;

    private static final String FIND_ALL_REQUESTS_STATUS_SQL = """
            SELECT *
            FROM request_status
            """;

    private static final String CREATE_REQUEST_STATUS_SQL = """
            INSERT INTO request_status (status_name)
            VALUES (?)
            """;

    private static final String UPDATE_REQUEST_STATUS_SQL = """
            UPDATE request_status rs
            SET status_name = ?
            WHERE rs.status_id = ?
            """;

    private static final String DELETE_REQUEST_STATUS_SQL = """
            DELETE
            FROM request_status rs
            WHERE rs.status_id = ?
            """;

    public static RequestStatusImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (RequestStatusImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RequestStatusImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private RequestStatusImplDao() {}

    @Override
    public Optional<RequestStatusEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_REQUEST_STATUS_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new RequestStatusEntity(resultSet.getInt(STATUS_ID),
                        resultSet.getString(STATUS_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() request_status: " + e.getMessage());
            throw new RequestStatusDaoException(e);
        }
        return Optional.empty();    }

    @Override
    public List<RequestStatusEntity> findAll() {
        List<RequestStatusEntity> allStatus = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_REQUESTS_STATUS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                allStatus.add(new RequestStatusEntity(resultSet.getInt(STATUS_ID),
                        resultSet.getString(STATUS_NAME)));
            }
            return allStatus;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() request_status: " + e.getMessage());
            throw new RequestStatusDaoException(e);
        }
    }

    @Override
    public RequestStatusEntity save(RequestStatusEntity status) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_REQUEST_STATUS_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, status.getStatusName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                status.setStatusId(generatedKeys.getInt(STATUS_ID));
            }
            return status;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() request_status: " + e.getMessage());
            throw new RequestStatusDaoException(e);
        }
    }

    @Override
    public void update(RequestStatusEntity status) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REQUEST_STATUS_SQL)) {
            preparedStatement.setString(1, status.getStatusName());
            preparedStatement.setInt(2, status.getStatusId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() request_status: " + e.getMessage());
            throw new RequestStatusDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REQUEST_STATUS_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() request_status: " + e.getMessage());
            throw new RequestStatusDaoException(e);
        }
    }
}
