package com.project.dao.testingOperationUtil;

import com.project.dao.impl.CarImplDao;
import com.project.entity.*;
import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.util.*;

@UtilityClass
public class CarUtil {

    public static void findCarByIdTest() {
        Optional<CarEntity> car = CarImplDao.getInstance().findById(3);
        System.out.println(car);
    }

    public static void findAllCarsTest() {
        List<CarEntity> allCars = CarImplDao.getInstance().findAll();
        System.out.println(allCars);
    }

    public static void savedCarTest() {
        CarImplDao carDao = CarImplDao.getInstance();
        CarEntity car = new CarEntity();
        car.setBrand(new BrandEntity(4, "Ford"));
        car.setModel(new ModelEntity(4, "Focus"));
        car.setBodyType(new BodyTypeEntity(5, "station wagon"));
        car.setYear(2019);
        car.setEngineVolume(BigDecimal.valueOf(1.50));
        car.setTransmission(new TransmissionEntity(2, "автоматическая"));
        car.setEngineType(new EngineTypeEntity(2, "бензиновый"));
        car.setCarStatus(new CarStatusEntity(2, "свободен"));
        CarEntity savedCar = carDao.save(car);
        System.out.println(savedCar);
    }

    public static void updateCarTest() {
        CarImplDao carDao = CarImplDao.getInstance();
        Optional<CarEntity> car = carDao.findById(4);
        System.out.println(car);

        car.ifPresent(carEntity -> {
            carEntity.setTransmission(new TransmissionEntity(1, "механическая"));
            carDao.update(carEntity);
        });
        System.out.println(carDao.findById(4));
    }

    public static void deleteCarTest() {
        CarImplDao carDao = CarImplDao.getInstance();
        boolean deleteResult = false;
        deleteResult = carDao.delete(6);
        System.out.println(deleteResult);
    }
}
