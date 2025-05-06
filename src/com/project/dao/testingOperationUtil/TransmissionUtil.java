package com.project.dao.testingOperationUtil;

import com.project.dao.impl.TransmissionImplDao;
import com.project.entity.TransmissionEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class TransmissionUtil {

    public static void findTransmissionByIdTest() {
        Optional<TransmissionEntity> transmissionById = TransmissionImplDao.getInstance().findById(2);
        System.out.println(transmissionById);
    }

    public static void findAllTransmissionsTest() {
        List<TransmissionEntity> transmissions = TransmissionImplDao.getInstance().findAll();
        System.out.println(transmissions);
    }

    public static void savedTransmissionTest() {
        TransmissionImplDao transmissionDao = TransmissionImplDao.getInstance();
        TransmissionEntity transmission = new TransmissionEntity();
        transmission.setTransmissionTypeName("test");
        TransmissionEntity savedTransmission = transmissionDao.save(transmission);
        System.out.println(savedTransmission);
    }

    public static void updateTransmissionTest() {
        TransmissionImplDao transmissionDao = TransmissionImplDao.getInstance();
        Optional<TransmissionEntity> transmissionById = transmissionDao.findById(3);
        System.out.println(transmissionById);
        transmissionById.ifPresent(transmissionEntity -> {
            transmissionEntity.setTransmissionTypeName("TEST1");
            transmissionDao.update(transmissionEntity);
        });
        System.out.println(transmissionDao.findById(3));
    }

    public static void deleteTransmissionTest() {
        TransmissionImplDao transmissionDao = TransmissionImplDao.getInstance();
        boolean deleteResult = transmissionDao.delete(3);
        System.out.println(deleteResult);
    }
}
