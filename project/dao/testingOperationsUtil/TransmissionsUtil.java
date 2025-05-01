package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.TransmissionsImplDao;
import com.project.entity.TransmissionsEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class TransmissionsUtil {

    public static void findTransmissionByIdTest() {
        Optional<TransmissionsEntity> transmissionById = TransmissionsImplDao.getInstance().findById(2);
        System.out.println(transmissionById);
    }

    public static void findAllTransmissionsTest() {
        List<TransmissionsEntity> transmissions = TransmissionsImplDao.getInstance().findAll();
        System.out.println(transmissions);
    }

    public static void savedTransmissionTest() {
        TransmissionsImplDao transmissionDao = TransmissionsImplDao.getInstance();
        TransmissionsEntity transmission = new TransmissionsEntity();
        transmission.setTransmissionTypeName("test");
        TransmissionsEntity savedTransmission = transmissionDao.save(transmission);
        System.out.println(savedTransmission);
    }

    public static void updateTransmissionTest() {
        TransmissionsImplDao transmissionDao = TransmissionsImplDao.getInstance();
        Optional<TransmissionsEntity> transmissionById = transmissionDao.findById(3);
        System.out.println(transmissionById);
        transmissionById.ifPresent(transmissionEntity -> {
            transmissionEntity.setTransmissionTypeName("TEST1");
            transmissionDao.update(transmissionEntity);
        });
        System.out.println(transmissionDao.findById(3));
    }

    public static void deleteTransmissionTest() {
        TransmissionsImplDao transmissionDao = TransmissionsImplDao.getInstance();
        boolean deleteResult = transmissionDao.delete(3);
        System.out.println(deleteResult);
    }
}
