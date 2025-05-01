package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.CarsImplDao;
import com.project.entity.*;
import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.util.*;

@UtilityClass
public class CarsUtil {

    public static void findCarByIdTest() {
        Optional<CarsEntity> car = CarsImplDao.getInstance().findById(3);
        System.out.println(car);
    }

    public static void findAllCarsTest() {
        List<CarsEntity> allCars = CarsImplDao.getInstance().findAll();
        System.out.println(allCars);
    }

    public static void savedCarTest() {
        CarsImplDao carDao = CarsImplDao.getInstance();
        CarsEntity car = new CarsEntity();
        car.setBrand(new BrandsEntity(4, "Ford"));
        car.setModel(new ModelsEntity(4, "Focus"));
        car.setBodyType(new BodyTypesEntity(5, "station wagon"));
        car.setYear(2019);
        car.setEngineVolume(BigDecimal.valueOf(1.50));
        car.setTransmission(new TransmissionsEntity(2, "автоматическая"));
        car.setEngineType(new EngineTypesEntity(2, "бензиновый"));
        car.setCarStatus(new CarStatusEntity(2, "свободен"));
        CarsEntity savedCar = carDao.save(car);
        System.out.println(savedCar);
    }

    public static void updateCarTest() {
        CarsImplDao carDao = CarsImplDao.getInstance();
        Optional<CarsEntity> car = carDao.findById(4);
        System.out.println(car);

        car.ifPresent(carEntity -> {
            carEntity.setTransmission(new TransmissionsEntity(1, "механическая"));
            carDao.update(carEntity);
        });
        System.out.println(carDao.findById(4));
    }

    public static void deleteCarTest() {
        CarsImplDao carDao = CarsImplDao.getInstance();
        boolean deleteResult = false;
        deleteResult = carDao.delete(6);
        System.out.println(deleteResult);
    }
}
