package com.project.dao.impl;

import com.project.dao.interfaceDao.RentalRequestDao;
import com.project.entity.RentalRequestEntity;
import com.project.exception.RentalRequestDaoException;
import com.project.utilConnection.ConnectionManager;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class RentalRequestImplDao implements RentalRequestDao {

    private static volatile RentalRequestImplDao INSTANCE;

private final CarImplDao carDao = CarImplDao.getInstance();
private final UserImplDao usersDao = UserImplDao.getInstance();
private final RequestStatusImplDao requestStatusDao = RequestStatusImplDao.getInstance();

private static final String REQUEST_ID = "request_id";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String TOTAL_PRICE = "total_price";
    private static final String CAR_ID = "car_id";
    private static final String USER_ID = "user_id";
    private static final String STATUS_ID = "status_id";

    private static final String FIND_RENTAL_REQUEST_BY_ID_SQL = """
        SELECT *
        FROM rental_requests rr
        WHERE rr.request_id=?
        """;
private static final String FIND_ALL_RENTAL_REQUEST_SQL = """
        SELECT *
        FROM rental_requests
        """;

private static final String CREATE_RENTAL_REQUEST_SQL = """
        INSERT INTO rental_requests (car_id, user_id, start_date, end_date, total_price, status_id)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

private static final String UPDATE_RENTAL_REQUEST_SQL = """
        UPDATE rental_requests rr
        SET car_id = ?,
        user_id = ?,
        start_date = ?,
        end_date = ?,
        total_price = ?,
        status_id = ?
        WHERE rr.request_id = ?
        """;

private static final String DELETE_RENTAL_REQUEST_SQL = """
        DELETE
        FROM rental_requests rr
        WHERE rr.request_id = ?
        """;

private RentalRequestImplDao() {}

    public static RentalRequestImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (RentalRequestImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RentalRequestImplDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<RentalRequestEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_RENTAL_REQUEST_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildRentalRequest(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println("Oшибка при findById() rental_request: " + e.getMessage());
            throw new RentalRequestDaoException(e);
        }
    }

    private RentalRequestEntity buildRentalRequest(ResultSet resultSet) throws SQLException {
        return new RentalRequestEntity(
                resultSet.getInt(REQUEST_ID),
                carDao.findById(resultSet.getInt(CAR_ID)).get(),
                usersDao.findById(resultSet.getLong(USER_ID)).get(),
                resultSet.getDate(START_DATE).toLocalDate(),
                resultSet.getDate(END_DATE).toLocalDate(),
                resultSet.getBigDecimal(TOTAL_PRICE),
                requestStatusDao.findById(resultSet.getInt(STATUS_ID)).get()
        );
    }

    @Override
    public List<RentalRequestEntity> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_RENTAL_REQUEST_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<RentalRequestEntity> list = new ArrayList<>();
            while (resultSet.next()) {
                findById(resultSet.getInt(REQUEST_ID)).ifPresent(list::add);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() rental_request: " + e.getMessage());
            throw new RentalRequestDaoException(e);
        }
    }

    @Override
    public RentalRequestEntity save(RentalRequestEntity rentalRequest) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RENTAL_REQUEST_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, rentalRequest.getCar().getCarId());
            preparedStatement.setLong(2, rentalRequest.getUser().getUserId());
            preparedStatement.setDate(3, Date.valueOf(rentalRequest.getStartDate()));
            preparedStatement.setDate(4, Date.valueOf(rentalRequest.getEndDate()));
            preparedStatement.setBigDecimal(5, rentalRequest.getTotalPrice());
            preparedStatement.setInt(6, rentalRequest.getRequestStatus().getStatusId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                rentalRequest.setRequestId(generatedKeys.getInt(REQUEST_ID));
            }
            return rentalRequest;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() rental_request: " + e.getMessage());
            throw new RentalRequestDaoException(e);
        }
    }

    @Override
    public void update(RentalRequestEntity rentalRequest) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RENTAL_REQUEST_SQL)) {
            preparedStatement.setInt(1, rentalRequest.getCar().getCarId());
            preparedStatement.setLong(2, rentalRequest.getUser().getUserId());
            preparedStatement.setDate(3, Date.valueOf(rentalRequest.getStartDate()));
            preparedStatement.setDate(4, Date.valueOf(rentalRequest.getEndDate()));
            preparedStatement.setBigDecimal(5, rentalRequest.getTotalPrice());
            preparedStatement.setInt(6, rentalRequest.getRequestStatus().getStatusId());
            preparedStatement.setInt(7, rentalRequest.getRequestId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() rental_request: " + e.getMessage());
            throw new RentalRequestDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RENTAL_REQUEST_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() rental_request: " + e.getMessage());
            throw new RentalRequestDaoException(e);
        }
    }
}
