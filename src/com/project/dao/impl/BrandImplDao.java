package com.project.dao.impl;

import com.project.dao.interfaceDao.BrandDao;
import com.project.entity.BrandEntity;
import com.project.exception.BrandDaoException;
import com.project.utilConnection.ConnectionManager;

import java.sql.*;
import java.util.*;

import static java.sql.Statement.*;

public class BrandImplDao implements BrandDao {

    private static volatile BrandImplDao INSTANCE;
    private static final String BRAND_ID = "brand_id";
    private static final String BRAND_NAME = "brand_name";

    private static final String FIND_BRAND_BY_ID_SQL = """
            SELECT *
            FROM brands b
            WHERE b.brand_id = ?
            """;

    private static final String FIND_ALL_BRANDS_SQL = """
            SELECT *
            FROM brands
            """;

    private static final String CREATE_BRAND_SQL = """
            INSERT INTO brands (brand_name)
            VALUES (?)
            """;

    private static final String UPDATE_BRAND_SQL = """
            UPDATE brands b
            SET brand_name = ?
            WHERE b.brand_id = ?
            """;

    private static final String DELETE_BRAND_SQL = """
            DELETE
            FROM brands b
            WHERE b.brand_id = ?
            """;

    public static BrandImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (BrandImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BrandImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private BrandImplDao() {
    }

    @Override
    public Optional<BrandEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BRAND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new BrandEntity(resultSet.getInt(BRAND_ID),
                        resultSet.getString(BRAND_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() brand: " + e.getMessage());
            throw new BrandDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<BrandEntity> findAll() {
        List<BrandEntity> brands = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BRANDS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                brands.add(new BrandEntity(resultSet.getInt(BRAND_ID),
                        resultSet.getString(BRAND_NAME)));
            }
            return brands;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() brand: " + e.getMessage());
            throw new BrandDaoException(e);
        }
    }

    @Override
    public BrandEntity save(BrandEntity brand) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_BRAND_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, brand.getBrandName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                brand.setBrandId(generatedKeys.getInt(BRAND_ID));
            }
            return brand;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() brand: " + e.getMessage());
            throw new BrandDaoException(e);
        }
    }

    @Override
    public void update(BrandEntity brand) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BRAND_SQL)) {
            preparedStatement.setString(1, brand.getBrandName());
            preparedStatement.setInt(2, brand.getBrandId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() brand: " + e.getMessage());
            throw new BrandDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BRAND_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() brand: " + e.getMessage());
            throw new BrandDaoException(e);
        }
    }
}
