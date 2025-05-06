package com.project.dao.impl;

import com.project.dao.interfaceDao.CarStatusDao;
import com.project.entity.CarStatusEntity;
import com.project.exception.CarStatusDaoException;
import com.project.utilConnection.ConnectionManager;

import java.sql.*;
import java.util.*;

public class CarStatusImplDao implements CarStatusDao {

    private static volatile CarStatusImplDao INSTANCE;
    private static final String STATUS_ID = "status_id";
    private static final String STATUS_NAME = "status_name";

    private static final String FIND_CAR_STATUS_BY_ID_SQL = """
            SELECT *
            FROM car_status cs
            WHERE cs.status_id = ?
            """;

    private static final String FIND_ALL_CAR_STATUS_SQL = """
            SELECT *
            FROM car_status
            """;

    private static final String CREATE_CAR_STATUS_SQL = """
            INSERT INTO car_status (status_name)
            VALUES (?)
            """;

    private static final String UPDATE_CAR_STATUS_SQL = """
            UPDATE car_status cs
            SET status_name = ?
            WHERE cs.status_id = ?
            """;

    private static final String DELETE_CAR_STATUS_SQL = """
            DELETE
            FROM car_status cs
            WHERE cs.status_id = ?
            """;

    public static CarStatusImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (CarStatusImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CarStatusImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private CarStatusImplDao() {
    }

    @Override
    public Optional<CarStatusEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CAR_STATUS_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new CarStatusEntity(resultSet.getInt(STATUS_ID),
                        resultSet.getString(STATUS_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() car_status: " + e.getMessage());
            throw new CarStatusDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<CarStatusEntity> findAll() {
        List<CarStatusEntity> allStatus = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CAR_STATUS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                allStatus.add(new CarStatusEntity(resultSet.getInt(STATUS_ID),
                        resultSet.getString(STATUS_NAME)));
            }
            return allStatus;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() car_status: " + e.getMessage());
            throw new CarStatusDaoException(e);
        }
    }

    @Override
    public CarStatusEntity save(CarStatusEntity status) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CAR_STATUS_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, status.getStatusName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                status.setStatusId(generatedKeys.getInt(STATUS_ID));
            }
            return status;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() car_status: " + e.getMessage());
            throw new CarStatusDaoException(e);
        }
    }

    @Override
    public void update(CarStatusEntity status) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CAR_STATUS_SQL)) {
            preparedStatement.setString(1, status.getStatusName());
            preparedStatement.setInt(2, status.getStatusId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() car_status: " + e.getMessage());
            throw new CarStatusDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CAR_STATUS_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() car_status: " + e.getMessage());
            throw new CarStatusDaoException(e);
        }
    }
}
