package com.project.dao.impl;

import com.project.dao.EngineTypesDao;
import com.project.entity.EngineTypesEntity;
import com.project.exception.EngineTypesDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

public class EngineTypesImplDao implements EngineTypesDao {

     private static volatile EngineTypesImplDao INSTANCE;
    private static final String ENGINE_TYPE_ID = "engine_type_id";
    private static final String ENGINE_TYPE_NAME = "engine_type_name";

    private static final String FIND_ENGINE_TYPE_BY_ID_SQL = """
            SELECT *
            FROM engine_types et
            WHERE et.engine_type_id = ?
            """;

    private static final String FIND_ALL_ENGINE_TYPES_SQL = """
            SELECT *
            FROM engine_types
            """;

    private static final String CREATE_ENGINE_TYPE_SQL = """
            INSERT INTO engine_types (engine_type_name)
            VALUES (?)
            """;

    private static final String UPDATE_ENGINE_TYPE_SQL = """
            UPDATE engine_types et
            SET engine_type_name = ?
            WHERE et.engine_type_id = ?
            """;

    private static final String DELETE_ENGINE_TYPE_SQL = """
            DELETE
            FROM engine_types et
            WHERE et.engine_type_id = ?
            """;

    public static EngineTypesImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (EngineTypesImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EngineTypesImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private EngineTypesImplDao() {
    }

    @Override
    public Optional<EngineTypesEntity> findById(Integer id) {
         try (Connection connection = ConnectionManager.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(FIND_ENGINE_TYPE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new EngineTypesEntity(resultSet.getInt(ENGINE_TYPE_ID),
                        resultSet.getString(ENGINE_TYPE_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() engine_type: " + e.getMessage());
            throw new EngineTypesDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<EngineTypesEntity> findAll() {
        List<EngineTypesEntity> engineTypes = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ENGINE_TYPES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                engineTypes.add(new EngineTypesEntity(resultSet.getInt(ENGINE_TYPE_ID),
                        resultSet.getString(ENGINE_TYPE_NAME)));
            }
            return engineTypes;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() engine_type: " + e.getMessage());
            throw new EngineTypesDaoException(e);
        }
    }

    @Override
    public EngineTypesEntity save(EngineTypesEntity engineType) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ENGINE_TYPE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, engineType.getEngineTypeName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                engineType.setEngineTypeId(generatedKeys.getInt(ENGINE_TYPE_ID));
            }
            return engineType;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() engine_type: " + e.getMessage());
            throw new EngineTypesDaoException(e);
        }
    }

    @Override
    public void update(EngineTypesEntity engineType) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ENGINE_TYPE_SQL)) {
            preparedStatement.setString(1, engineType.getEngineTypeName());
            preparedStatement.setInt(2, engineType.getEngineTypeId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() engine_type: " + e.getMessage());
            throw new EngineTypesDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ENGINE_TYPE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() engine_type: " + e.getMessage());
            throw new EngineTypesDaoException(e);
        }
    }
}
