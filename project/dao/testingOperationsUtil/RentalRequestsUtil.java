package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.CarsImplDao;
import com.project.dao.impl.RentalRequestsImplDao;
import com.project.dao.impl.RequestStatusImplDao;
import com.project.dao.impl.UsersImplDao;
import com.project.entity.CarsEntity;
import com.project.entity.RentalRequestsEntity;
import com.project.entity.RequestStatusEntity;
import com.project.entity.UsersEntity;
import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@UtilityClass
public class RentalRequestsUtil {

    public static void findRentalRequestByIdTest() {
        Optional<RentalRequestsEntity> rentalRequest = RentalRequestsImplDao.getInstance().findById(3);
        System.out.println(rentalRequest);
    }

    public static void findAllRentalRequestsTest() {
        List<RentalRequestsEntity> allRentalRequests = RentalRequestsImplDao.getInstance().findAll();
        System.out.println(allRentalRequests);
    }

    public static void savedRentalRequestTest() {
        RentalRequestsImplDao rentalRequestDao = RentalRequestsImplDao.getInstance();
        CarsImplDao carDao = CarsImplDao.getInstance();
        UsersImplDao userDao = UsersImplDao.getInstance();
        RequestStatusImplDao requestStatusDao = RequestStatusImplDao.getInstance();

        Optional<CarsEntity> car = carDao.findById(1);
        Optional<UsersEntity> user = userDao.findById(1L);
        Optional<RequestStatusEntity> status = requestStatusDao.findById(1);
        if (car.isPresent() && user.isPresent() && status.isPresent()) {
            RentalRequestsEntity rentalRequest = new RentalRequestsEntity(
                    4, car.get(), user.get(), LocalDate.of(2025, 5, 18),
                    LocalDate.of(2025, 5, 20), BigDecimal.valueOf(200.00), status.get());
            RentalRequestsEntity savedRentalRequest = rentalRequestDao.save(rentalRequest);
            System.out.println(savedRentalRequest);
        }
    }

    public static void updateRentalRequestTest() {
        RentalRequestsImplDao rentalRequestDao = RentalRequestsImplDao.getInstance();
        Optional<RentalRequestsEntity> rentalRequest = rentalRequestDao.findById(2);
        System.out.println(rentalRequest);

        rentalRequest.ifPresent(rentalRequestEntity -> {
            rentalRequestEntity.setTotalPrice(BigDecimal.valueOf(400.00));
            rentalRequestDao.update(rentalRequestEntity);
        });
        System.out.println(rentalRequestDao.findById(2));
    }

    public static void deleteRentalRequestTest() {
        RentalRequestsImplDao rentalRequestDao = RentalRequestsImplDao.getInstance();
        boolean deleteResult = false;
        deleteResult = rentalRequestDao.delete(4);
        System.out.println(deleteResult);
    }
}
