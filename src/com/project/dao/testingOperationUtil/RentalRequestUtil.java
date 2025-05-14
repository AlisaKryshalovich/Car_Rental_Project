package com.project.dao.testingOperationUtil;

import com.project.dao.impl.CarImplDao;
import com.project.dao.impl.RentalRequestImplDao;
import com.project.dao.impl.RequestStatusImplDao;
import com.project.dao.impl.UserImplDao;
import com.project.entity.CarEntity;
import com.project.entity.RentalRequestEntity;
import com.project.entity.RequestStatusEntity;
import com.project.entity.UserEntity;
import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@UtilityClass
public class RentalRequestUtil {

    public static void findRentalRequestByIdTest() {
        Optional<RentalRequestEntity> rentalRequest = RentalRequestImplDao.getInstance().findById(3);
        System.out.println(rentalRequest);
    }

    public static void findAllRentalRequestsTest() {
        List<RentalRequestEntity> allRentalRequests = RentalRequestImplDao.getInstance().findAll();
        System.out.println(allRentalRequests);
    }

    public static void savedRentalRequestTest() {
        RentalRequestImplDao rentalRequestDao = RentalRequestImplDao.getInstance();
        CarImplDao carDao = CarImplDao.getInstance();
        UserImplDao userDao = UserImplDao.getInstance();
        RequestStatusImplDao requestStatusDao = RequestStatusImplDao.getInstance();

        Optional<CarEntity> car = carDao.findById(1);
        Optional<UserEntity> user = userDao.findById(1L);
        Optional<RequestStatusEntity> status = requestStatusDao.findById(1);
        if (car.isPresent() && user.isPresent() && status.isPresent()) {
            RentalRequestEntity rentalRequest = new RentalRequestEntity(
                    4, car.get(), user.get(), LocalDate.of(2025, 5, 18),
                    LocalDate.of(2025, 5, 20), BigDecimal.valueOf(200.00), status.get());
            RentalRequestEntity savedRentalRequest = rentalRequestDao.save(rentalRequest);
            System.out.println(savedRentalRequest);
        }
    }

    public static void updateRentalRequestTest() {
        RentalRequestImplDao rentalRequestDao = RentalRequestImplDao.getInstance();
        Optional<RentalRequestEntity> rentalRequest = rentalRequestDao.findById(2);
        System.out.println(rentalRequest);

        rentalRequest.ifPresent(rentalRequestEntity -> {
            rentalRequestEntity.setTotalPrice(BigDecimal.valueOf(400.00));
            rentalRequestDao.update(rentalRequestEntity);
        });
        System.out.println(rentalRequestDao.findById(2));
    }

    public static void deleteRentalRequestTest() {
        RentalRequestImplDao rentalRequestDao = RentalRequestImplDao.getInstance();
        boolean deleteResult = false;
        deleteResult = rentalRequestDao.delete(4);
        System.out.println(deleteResult);
    }
}
