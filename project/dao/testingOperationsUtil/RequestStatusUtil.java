package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.RequestStatusImplDao;
import com.project.entity.RequestStatusEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class RequestStatusUtil {

    public static void findRequestStatusByIdTest() {
        Optional<RequestStatusEntity> status = RequestStatusImplDao.getInstance().findById(2);
        System.out.println(status);
    }

    public static void findAllRequestStatusTest() {
        List<RequestStatusEntity> allStatus = RequestStatusImplDao.getInstance().findAll();
        System.out.println(allStatus);
    }

    public static void savedRequestStatusTest() {
        RequestStatusImplDao requestStatusDao = RequestStatusImplDao.getInstance();
        RequestStatusEntity requestStatus = new RequestStatusEntity();
        requestStatus.setStatusName("test");
        RequestStatusEntity savedStatus = requestStatusDao.save(requestStatus);
        System.out.println(savedStatus);
    }

    public static void updateRequestStatusTest() {
        RequestStatusImplDao requestStatusDao = RequestStatusImplDao.getInstance();
        Optional<RequestStatusEntity> requestStatusById = requestStatusDao.findById(6);
        System.out.println(requestStatusById);
        requestStatusById.ifPresent(requestStatusEntity -> {
            requestStatusEntity.setStatusName("TEST1");
            requestStatusDao.update(requestStatusEntity);
        });
        System.out.println(requestStatusDao.findById(6));
    }

    public static void deleteRequestStatusTest() {
        RequestStatusImplDao requestStatusDao = RequestStatusImplDao.getInstance();
        boolean deleteResult = requestStatusDao.delete(6);
        System.out.println(deleteResult);
    }
}
