package com.project.dao.impl;

import com.project.dao.interfaceDao.CarDao;

import com.project.entity.*;
import com.project.exception.CarStatusDaoException;
import com.project.exception.CarDaoException;
import com.project.utilConnection.ConnectionManager;
import java.sql.*;
import java.util.*;

import static java.sql.Statement.*;

public class CarImplDao implements CarDao {

    private static volatile CarImplDao INSTANCE;

    private final ModelImplDao modelDao = ModelImplDao.getInstance();
    private final BodyTypeImplDao bodyTypeDao = BodyTypeImplDao.getInstance();
    private final TransmissionImplDao transmissionDao = TransmissionImplDao.getInstance();
    private final EngineTypeImplDao engineTypeDao = EngineTypeImplDao.getInstance();
    private final CarStatusImplDao carStatusDao = CarStatusImplDao.getInstance();
    private final BrandImplDao brandDao = BrandImplDao.getInstance();

    private static final String CAR_ID = "car_id";
    private static final String YEAR = "year";
    private static final String ENGINE_VOLUME = "engine_volume";
    private static final String BRAND_ID = "brand_id";
    private static final String MODEL_ID = "model_id";
    private static final String BODY_TYPE_ID = "body_type_id";
    private static final String TRANSMISSION_ID = "transmission_id";
    private static final String ENGINE_TYPE_ID = "engine_type_id";
    private static final String CAR_STATUS_ID = "car_status_id";

    private static final String FIND_CAR_BY_ID_SQL = """
            SELECT *
            FROM cars c
            WHERE c.car_id = ?
            """;

    private static final String FIND_ALL_CARS_SQL = """
            SELECT *
            FROM cars
            """;

    private static final String CREATE_CAR_SQL = """
            INSERT INTO cars (brand_id, model_id, body_type_id, year, engine_volume, transmission_id, engine_type_id, car_status_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_CAR_SQL = """
            UPDATE cars c
            SET brand_id = ?,
            model_id = ?,
            body_type_id = ?,
            year = ?,
            engine_volume = ?,
            transmission_id = ?,
            engine_type_id = ?,
            car_status_id = ?
            WHERE c.car_id = ?
            """;

    private static final String DELETE_CAR_SQL = """
            DELETE
            FROM cars c
            WHERE c.car_id = ?
            """;

    private CarImplDao() {
    }

    public static CarImplDao getInstance() {
        if (INSTANCE == null) {
            synchronized (CarImplDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CarImplDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<CarEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CAR_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildCar(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println("Oшибка при findById() car: " + e.getMessage());
            throw new CarStatusDaoException(e);
        }
    }

    private CarEntity buildCar(ResultSet resultSet) throws SQLException {
        return new CarEntity(
                resultSet.getInt(CAR_ID),
                brandDao.findById(resultSet.getInt(BRAND_ID)).get(),
                modelDao.findById(resultSet.getInt(MODEL_ID)).get(),
                bodyTypeDao.findById(resultSet.getInt(BODY_TYPE_ID)).get(),
                resultSet.getInt(YEAR),
                resultSet.getBigDecimal(ENGINE_VOLUME),
                transmissionDao.findById(resultSet.getInt(TRANSMISSION_ID)).get(),
                engineTypeDao.findById(resultSet.getInt(ENGINE_TYPE_ID)).get(),
                carStatusDao.findById(resultSet.getInt(CAR_STATUS_ID)).get()
        );
    }

    @Override
    public List<CarEntity> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CARS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<CarEntity> list = new ArrayList<>();
            while (resultSet.next()) {
                findById(resultSet.getInt(CAR_ID)).ifPresent(list::add);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Oшибка при findAll() cars: " + e.getMessage());
            throw new CarDaoException(e);
        }
    }

    @Override
    public CarEntity save(CarEntity car) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CAR_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, car.getBrand().getBrandId());
            preparedStatement.setInt(2, car.getModel().getModelId());
            preparedStatement.setInt(3, car.getBodyType().getBodyTypeId());
            preparedStatement.setInt(4, car.getYear());
            preparedStatement.setBigDecimal(5, car.getEngineVolume());
            preparedStatement.setInt(6, car.getTransmission().getTransmissionId());
            preparedStatement.setInt(7, car.getEngineType().getEngineTypeId());
            preparedStatement.setInt(8, car.getCarStatus().getStatusId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setCarId(generatedKeys.getInt(CAR_ID));
            }
            return car;
        } catch (SQLException e) {
            System.err.println("Oшибка при save() car: " + e.getMessage());
            throw new CarDaoException(e);
        }
    }

    @Override
    public void update(CarEntity car) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CAR_SQL)) {
            preparedStatement.setInt(1, car.getBrand().getBrandId());
            preparedStatement.setInt(2, car.getModel().getModelId());
            preparedStatement.setInt(3, car.getBodyType().getBodyTypeId());
            preparedStatement.setInt(4, car.getYear());
            preparedStatement.setBigDecimal(5, car.getEngineVolume());
            preparedStatement.setInt(6, car.getTransmission().getTransmissionId());
            preparedStatement.setInt(7, car.getEngineType().getEngineTypeId());
            preparedStatement.setInt(8, car.getCarStatus().getStatusId());
            preparedStatement.setInt(9, car.getCarId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Oшибка при update() car: " + e.getMessage());
            throw new CarDaoException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CAR_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Oшибка при delete() car: " + e.getMessage());
            throw new CarDaoException(e);
        }
    }
}
