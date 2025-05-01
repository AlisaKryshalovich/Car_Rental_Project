package com.project.dao.impl;

import com.project.dao.BrandsDao;
import com.project.entity.BrandsEntity;
import com.project.exception.BrandsDaoException;
import com.project.utilConnection.ConnectionManager;

import java.sql.*;
import java.util.*;

public class BrandsImplDao implements BrandsDao {

    private static volatile BrandsImplDao INSTANCE;
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

    public static BrandsImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (BrandsImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BrandsImplDao();
                }
            }
        }
        return INSTANCE;
    }

    private BrandsImplDao() {
    }

    @Override
    public Optional<BrandsEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BRAND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new BrandsEntity(resultSet.getInt(BRAND_ID),
                        resultSet.getString(BRAND_NAME)));
            }
        } catch (
                SQLException e) {
            System.err.println("Oшибка при findById() brand: " + e.getMessage());
            throw new BrandsDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<BrandsEntity> findAll() {
        List<BrandsEntity> brands = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BRANDS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                brands.add(new BrandsEntity(resultSet.getInt(BRAND_ID),
                        resultSet.getString(BRAND_NAME)));
            }
            return brands;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() brand: " + e.getMessage());
            throw new BrandsDaoException(e);
        }
    }

    @Override
    public BrandsEntity save(BrandsEntity brand) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_BRAND_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, brand.getBrandName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                brand.setBrandId(generatedKeys.getInt(BRAND_ID));
            }
            return brand;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() brand: " + e.getMessage());
            throw new BrandsDaoException(e);
        }
    }

    @Override
    public void update(BrandsEntity brand) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BRAND_SQL)) {
            preparedStatement.setString(1, brand.getBrandName());
            preparedStatement.setInt(2, brand.getBrandId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() brand: " + e.getMessage());
            throw new BrandsDaoException(e);
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
            throw new BrandsDaoException(e);
        }
    }
}
