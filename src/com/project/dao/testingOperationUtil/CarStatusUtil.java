package com.project.dao.testingOperationUtil;

import com.project.dao.impl.CarStatusImplDao;
import com.project.entity.CarStatusEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class CarStatusUtil {

    public static void findCarStatusByIdTest() {
        Optional<CarStatusEntity> status = CarStatusImplDao.getInstance().findById(2);
        System.out.println(status);
    }

    public static void findAllCarStatusTest() {
        List<CarStatusEntity> allStatus = CarStatusImplDao.getInstance().findAll();
        System.out.println(allStatus);
    }

    public static void savedCarStatusTest() {
        CarStatusImplDao carStatusDao = CarStatusImplDao.getInstance();
        CarStatusEntity carStatus = new CarStatusEntity();
        carStatus.setStatusName("test");
        CarStatusEntity savedStatus = carStatusDao.save(carStatus);
        System.out.println(savedStatus);
    }

    public static void updateCarStatusTest() {
        CarStatusImplDao carStatusDao = CarStatusImplDao.getInstance();
        Optional<CarStatusEntity> carStatusById = carStatusDao.findById(3);
        System.out.println(carStatusById);
        carStatusById.ifPresent(carStatusEntity -> {
            carStatusEntity.setStatusName("TEST1");
            carStatusDao.update(carStatusEntity);
        });
        System.out.println(carStatusDao.findById(3));
    }

    public static void deleteCarStatusTest() {
        CarStatusImplDao carStatusDao = CarStatusImplDao.getInstance();
        boolean deleteResult = carStatusDao.delete(3);
        System.out.println(deleteResult);
    }
}
