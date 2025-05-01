package com.project.dao.impl;

import com.project.dao.BodyTypesDao;
import com.project.entity.BodyTypesEntity;
import com.project.exception.BodyTypeDaoException;
import com.project.utilConnection.ConnectionManager;

import java.sql.*;
import java.util.*;

public class BodyTypesImplDao implements BodyTypesDao {

    private static volatile BodyTypesImplDao INSTANCE;
    private static final String BODY_TYPE_ID = "body_type_id";
    private static final String BODY_TYPE_NAME = "body_type_name";

    private static final String FIND_BODY_TYPE_BY_ID_SQL = """
            SELECT *
            FROM body_types bt
            WHERE bt.body_type_id = ?
            """;

    private static final String FIND_ALL_BODY_TYPES_SQL = """
            SELECT *
            FROM body_types
            """;

    private static final String CREATE_BODY_TYPE_SQL = """
            INSERT INTO body_types (body_type_name)
            VALUES (?)
            """;

    private static final String UPDATE_BODY_TYPE_SQL = """
            UPDATE body_types bt
            SET body_type_name = ?
            WHERE bt.body_type_id = ?
            """;

    private static final String DELETE_BODY_TYPE_SQL = """
            DELETE
            FROM body_types bt
            WHERE bt.body_type_id = ?
            """;

    public static BodyTypesImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (BodyTypesImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BodyTypesImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private BodyTypesImplDao() {
    }

    @Override
    public Optional<BodyTypesEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BODY_TYPE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new BodyTypesEntity(resultSet.getInt(BODY_TYPE_ID),
                        resultSet.getString(BODY_TYPE_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() body_type: " + e.getMessage());
            throw new BodyTypeDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<BodyTypesEntity> findAll() {
        List<BodyTypesEntity> bodyTypes = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BODY_TYPES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                bodyTypes.add(new BodyTypesEntity(resultSet.getInt(BODY_TYPE_ID),
                        resultSet.getString(BODY_TYPE_NAME)));
            }
            return bodyTypes;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() body_type: " + e.getMessage());
            throw new BodyTypeDaoException(e);
        }
    }

    @Override
    public BodyTypesEntity save(BodyTypesEntity bodyType) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_BODY_TYPE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bodyType.getBodyTypeName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bodyType.setBodyTypeId(generatedKeys.getInt(BODY_TYPE_ID));
            }
            return bodyType;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() body_type: " + e.getMessage());
            throw new BodyTypeDaoException(e);
        }
    }

    @Override
    public void update(BodyTypesEntity bodyTypes) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BODY_TYPE_SQL)) {
            preparedStatement.setString(1, bodyTypes.getBodyTypeName());
            preparedStatement.setInt(2, bodyTypes.getBodyTypeId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() body_type: " + e.getMessage());
            throw new BodyTypeDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BODY_TYPE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() body_type: " + e.getMessage());
            throw new BodyTypeDaoException(e);
        }
    }
}
