package com.project.dao.impl;

import com.project.dao.TransmissionsDao;
import com.project.entity.TransmissionsEntity;
import com.project.exception.TransmissionsDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

public class TransmissionsImplDao implements TransmissionsDao {

     private static volatile TransmissionsImplDao INSTANCE;
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

    public static TransmissionsImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (TransmissionsImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TransmissionsImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private TransmissionsImplDao() {
    }

    @Override
    public Optional<TransmissionsEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TRANSMISSION_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new TransmissionsEntity(resultSet.getInt(TRANSMISSION_ID),
                        resultSet.getString(TRANSMISSION_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() transmission: " + e.getMessage());
            throw new TransmissionsDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<TransmissionsEntity> findAll() {
        List<TransmissionsEntity> transmissions = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TRANSMISSIONS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                transmissions.add(new TransmissionsEntity(resultSet.getInt(TRANSMISSION_ID),
                        resultSet.getString(TRANSMISSION_NAME)));
            }
            return transmissions;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() transmission: " + e.getMessage());
            throw new TransmissionsDaoException(e);
        }
    }

    @Override
    public TransmissionsEntity save(TransmissionsEntity transmission) {
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
            throw new TransmissionsDaoException(e);
        }
    }

    @Override
    public void update(TransmissionsEntity transmission) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRANSMISSION_SQL)) {
            preparedStatement.setString(1, transmission.getTransmissionTypeName());
            preparedStatement.setInt(2, transmission.getTransmissionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() transmission: " + e.getMessage());
            throw new TransmissionsDaoException(e);
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
            throw new TransmissionsDaoException(e);
        }
    }
}
