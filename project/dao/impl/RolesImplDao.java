package com.project.dao.impl;

import com.project.dao.RolesDao;
import com.project.entity.RolesEntity;
import com.project.exception.RolesDaoExcepion;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

public class RolesImplDao implements RolesDao {

    private static volatile RolesImplDao INSTANCE;
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

    private RolesImplDao() {
    }

    public static RolesImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (RolesImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RolesImplDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<RolesEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROLE_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new RolesEntity(resultSet.getInt(ROLE_ID),
                        resultSet.getString(ROLE_NAME)));
            }
        } catch (SQLException e) {
            System.err.println("Oшибка при findById() role: " + e.getMessage());
            throw new RolesDaoExcepion(e);
        }
        return Optional.empty();
    }

    @Override
    public List<RolesEntity> findAll() {
        List<RolesEntity> roles = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ROLES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                roles.add(new RolesEntity(resultSet.getInt(ROLE_ID),
                        resultSet.getString(ROLE_NAME)));
            }
            return roles;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() role: " + e.getMessage());
            throw new RolesDaoExcepion(e);
        }
    }

    @Override
    public RolesEntity save(RolesEntity role) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROLE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                role.setRoleId(generatedKeys.getInt(ROLE_ID));
            }
            return role;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() role: " + e.getMessage());
            throw new RolesDaoExcepion(e);
        }
    }

    @Override
    public void update(RolesEntity role) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_SQL)) {
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.setInt(2, role.getRoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() role: " + e.getMessage());
            throw new RolesDaoExcepion(e);
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
            throw new RolesDaoExcepion(e);
        }
    }
}