package com.project.dao.impl;

import com.project.dao.RoleDao;
import com.project.entity.RoleEntity;
import com.project.exception.DaoException;
import com.project.exception.RoleDaoExcepion;
import com.project.utilConnection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleImplDao implements RoleDao {

    private static volatile RoleImplDao INSTANCE;
    private static final String ROLE_ID = "role_id";
    private static final String ROLE_NAME = "role_name";


    private static final String FIND_ROLE_BY_ID_SQL = """
            SELECT * 
            FROM roles r 
            WHERE r.role_id = ?
            """;

    private static final String FIND_ALL_ROLES_SQL = """
            SELECT * 
            FROM roles
            """;

    private static final String CREATE_ROLE_SQL = """
            INSERT INTO roles (role_name) 
            VALUES (?)
            """;

    private static final String UPDATE_ROLE_SQL = """
            UPDATE roles r
            SET role_name = ?
            WHERE r.role_id = ?
            """;

    private static final String DELETE_ROLE_SQL = """
            DELETE
            FROM roles r
            WHERE r.role_id = ?
            """;

    private RoleImplDao() {
    }

    public static RoleImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (RoleImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RoleImplDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<RoleEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROLE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new RoleEntity(resultSet.getInt(ROLE_ID),
                        resultSet.getString(ROLE_NAME)));
            }
        } catch (SQLException e) {
            System.err.println("Oшибка при findById() role: " + e.getMessage());
            throw new RoleDaoExcepion(e);
        }
        return Optional.empty();
    }

    @Override
    public List<RoleEntity> findAll() {
        List<RoleEntity> roles = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ROLES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                roles.add(new RoleEntity(resultSet.getInt(ROLE_ID),
                        resultSet.getString(ROLE_NAME)));
            }
            return roles;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() role: " + e.getMessage());
            throw new RoleDaoExcepion(e);
        }
    }

    @Override
    public RoleEntity save(RoleEntity role) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_ROLE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                role.setId(generatedKeys.getInt(ROLE_ID));
            }
            return role;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() role: " + e.getMessage());
            throw new RoleDaoExcepion(e);
        }
    }

    @Override
    public void update(RoleEntity role) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_SQL)) {
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.setInt(2, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() role: " + e.getMessage());
            throw new RoleDaoExcepion(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() role: " + e.getMessage());
            throw new RoleDaoExcepion(e);
        }
    }
}