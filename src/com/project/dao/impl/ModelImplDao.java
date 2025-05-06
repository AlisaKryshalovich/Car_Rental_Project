package com.project.dao.impl;

import com.project.dao.interfaceDao.ModelDao;
import com.project.entity.ModelEntity;
import com.project.exception.ModelDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

public class ModelImplDao implements ModelDao {

    private static volatile ModelImplDao INSTANCE;
    private static final String MODEL_ID = "model_id";
    private static final String MODEL_NAME = "model_name";

    private static final String FIND_MODEL_BY_ID_SQL = """
            SELECT *
            FROM models m
            WHERE m.model_id = ?
            """;

    private static final String FIND_ALL_MODELS_SQL = """
            SELECT *
            FROM models
            """;

    private static final String CREATE_MODEL_SQL = """
            INSERT INTO models (model_name)
            VALUES (?)
            """;

    private static final String UPDATE_MODEL_SQL = """
            UPDATE models m
            SET model_name = ?
            WHERE m.model_id = ?
            """;

    private static final String DELETE_MODEL_SQL = """
            DELETE
            FROM models m
            WHERE m.model_id = ?
            """;

    public static ModelImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ModelImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ModelImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private ModelImplDao() {
    }

    @Override
    public Optional<ModelEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_MODEL_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ModelEntity(resultSet.getInt(MODEL_ID),
                        resultSet.getString(MODEL_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() model: " + e.getMessage());
            throw new ModelDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<ModelEntity> findAll() {
        List<ModelEntity> models = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_MODELS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                models.add(new ModelEntity(resultSet.getInt(MODEL_ID),
                        resultSet.getString(MODEL_NAME)));
            }
            return models;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() model: " + e.getMessage());
            throw new ModelDaoException(e);
        }
    }

    @Override
    public ModelEntity save(ModelEntity model) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_MODEL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, model.getModelName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                model.setModelId(generatedKeys.getInt(MODEL_ID));
            }
            return model;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() model: " + e.getMessage());
            throw new ModelDaoException(e);
        }
    }

    @Override
    public void update(ModelEntity model) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MODEL_SQL)) {
            preparedStatement.setString(1, model.getModelName());
            preparedStatement.setInt(2, model.getModelId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() model: " + e.getMessage());
            throw new ModelDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MODEL_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() model: " + e.getMessage());
            throw new ModelDaoException(e);
        }
    }
}
