package com.project.dao.impl;

import com.project.dao.ContactsDao;
import com.project.entity.ContactsEntity;
import com.project.entity.UsersEntity;
import com.project.exception.ContactsDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

public class ContactsImplDao implements ContactsDao {

    private final UsersImplDao usersImplDao = UsersImplDao.getInstance();

    private static volatile ContactsImplDao INSTANCE;

    private static final String CONTACT_ID = "contact_id";
    private static final String CONTACT_EMAIL = "email";
    private static final String CONTACT_PHONE = "phone";
    private static final String USER_ID = "user_id";

    private static final String FIND_CONTACT_BY_ID_SQL = """
            SELECT *
            FROM contacts c
            WHERE c.contact_id = ?
            """;

    private static final String FIND_ALL_CONTACTS_SQL = """
            SELECT *
            FROM contacts
            """;

    private static final String CREATE_CONTACT_SQL = """
            INSERT INTO contacts (user_id, email, phone)
            VALUES (?, ?, ?)
            """;

    private static final String UPDATE_CONTACT_SQL = """
            UPDATE contacts c
            SET user_id = ?,
            email = ?,
            phone = ?
            WHERE c.contact_id = ?
            """;

    private static final String DELETE_CONTACT_BY_ID_SQL = """
            DELETE
            FROM contacts c
            WHERE c.contact_id = ?
            """;

    private ContactsImplDao() {
    }

    public static ContactsImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ContactsImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ContactsImplDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<ContactsEntity> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CONTACT_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Optional<UsersEntity> user = usersImplDao.findById(resultSet.getLong(USER_ID));
                return user.map(usersEntity -> {
                    try {
                        return new ContactsEntity(
                                resultSet.getLong(CONTACT_ID),
                                usersEntity,
                                resultSet.getString(CONTACT_EMAIL),
                                resultSet.getString(CONTACT_PHONE)
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (SQLException e) {
            System.err.println("Oшибка при findById() contact: " + e.getMessage());
            throw new ContactsDaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<ContactsEntity> findAll() {
        List<ContactsEntity> contacts = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CONTACTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Optional<UsersEntity> user = usersImplDao.findById(resultSet.getLong(USER_ID));
                user.ifPresent(usersEntity -> {
                    try {
                        contacts.add(new ContactsEntity(
                                resultSet.getLong(CONTACT_ID),
                                usersEntity,
                                resultSet.getString(CONTACT_EMAIL),
                                resultSet.getString(CONTACT_PHONE)
                        ));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() contact: " + e.getMessage());
            throw new ContactsDaoException(e);
        }
        return contacts;
    }

//    private static ContactsEntity buildContact(ResultSet resultSet) throws SQLException {
//        RoleEntity role = new RoleEntity(resultSet.getInt(ROLE_ID),
//                resultSet.getString(ROLE_NAME));
//        UsersEntity user = new UsersEntity(
//                resultSet.getLong(USER_ID),
//                resultSet.getString(USER_FIRST_NAME),
//                resultSet.getString(USER_LAST_NAME),
//                resultSet.getInt(USER_AGE),
//                resultSet.getString(USER_PASSWORD),
//                resultSet.getString(USER_PASSPORT_NO),
//                role
//        );
//        return new ContactsEntity(resultSet.getLong(CONTACT_ID),
//                resultSet.getLong(USER_ID),
//                resultSet.getString(CONTACT_EMAIL),
//                resultSet.getString(CONTACT_PHONE)
//        );

//    private static ContactsEntity buildContact(ResultSet resultSet) throws SQLException {
//        RoleEntity role = new RoleEntity(resultSet.getInt(ROLE_ID),
//                resultSet.getString(ROLE_NAME));
//        UsersEntity user = new UsersEntity(
//                resultSet.getLong(USER_ID),
//                resultSet.getString(USER_FIRST_NAME),
//                resultSet.getString(USER_LAST_NAME),
//                resultSet.getInt(USER_AGE),
//                resultSet.getString(USER_PASSWORD),
//                resultSet.getString(USER_PASSPORT_NO),
//                role
//        );
//        return new ContactsEntity(resultSet.getLong(CONTACT_ID),
//                user,
//                resultSet.getString(CONTACT_EMAIL),
//                resultSet.getString(CONTACT_PHONE)
//        );
//    }

    @Override
    public ContactsEntity save(ContactsEntity contact) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CONTACT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, contact.getUser().getUserId());
            preparedStatement.setString(2, contact.getEmail());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                contact.setContactId(generatedKeys.getLong(CONTACT_ID));
            }
            return contact;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() contact: " + e.getMessage());
            throw new ContactsDaoException(e);
        }
    }

    @Override
    public void update(ContactsEntity contact) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT_SQL)) {
            preparedStatement.setLong(1, contact.getUser().getUserId());
            preparedStatement.setString(2, contact.getEmail());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.setLong(4, contact.getContactId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() contact: " + e.getMessage());
            throw new ContactsDaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() contact: " + e.getMessage());
            throw new ContactsDaoException(e);
        }
    }
}
