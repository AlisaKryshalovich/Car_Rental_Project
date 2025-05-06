package com.project.dao.impl;

import com.project.dao.interfaceDao.TransmissionDao;
import com.project.entity.TransmissionEntity;
import com.project.exception.TransmissionDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

public class TransmissionImplDao implements TransmissionDao {

     private static volatile TransmissionImplDao INSTANCE;
    private static final String TRANSMISSION_ID = "transmission_id";
    private static final String TRANSMISSION_NAME = "transmission_type_name";

    private static final String FIND_TRANSMISSION_BY_ID_SQL = """
            SELECT *
            FROM transmissions t
            WHERE t.transmission_id = ?
            """;

    private static final String FIND_ALL_TRANSMISSIONS_SQL = """
            SELECT *
            FROM transmissions
            """;

    private static final String CREATE_TRANSMISSION_SQL = """
            INSERT INTO transmissions (transmission_type_name)
            VALUES (?)
            """;

    private static final String UPDATE_TRANSMISSION_SQL = """
            UPDATE transmissions t
            SET transmission_type_name = ?
            WHERE t.transmission_id = ?
            """;

    private static final String DELETE_TRANSMISSION_SQL = """
            DELETE
            FROM transmissions t
            WHERE t.transmission_id = ?
            """;

    public static TransmissionImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (TransmissionImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TransmissionImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private TransmissionImplDao() {
    }

    @Override
    public Optional<TransmissionEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TRANSMISSION_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new TransmissionEntity(resultSet.getInt(TRANSMISSION_ID),
                        resultSet.getString(TRANSMISSION_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() transmission: " + e.getMessage());
            throw new TransmissionDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<TransmissionEntity> findAll() {
        List<TransmissionEntity> transmissions = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TRANSMISSIONS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                transmissions.add(new TransmissionEntity(resultSet.getInt(TRANSMISSION_ID),
                        resultSet.getString(TRANSMISSION_NAME)));
            }
            return transmissions;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() transmission: " + e.getMessage());
            throw new TransmissionDaoException(e);
        }
    }

    @Override
    public TransmissionEntity save(TransmissionEntity transmission) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TRANSMISSION_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, transmission.getTransmissionTypeName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transmission.setTransmissionId(generatedKeys.getInt(TRANSMISSION_ID));
            }
            return transmission;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() transmission: " + e.getMessage());
            throw new TransmissionDaoException(e);
        }
    }

    @Override
    public void update(TransmissionEntity transmission) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRANSMISSION_SQL)) {
            preparedStatement.setString(1, transmission.getTransmissionTypeName());
            preparedStatement.setInt(2, transmission.getTransmissionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() transmission: " + e.getMessage());
            throw new TransmissionDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRANSMISSION_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() transmission: " + e.getMessage());
            throw new TransmissionDaoException(e);
        }
    }
}
