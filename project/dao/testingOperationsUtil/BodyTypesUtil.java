package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.BodyTypesImplDao;
import com.project.entity.BodyTypesEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class BodyTypesUtil {

    public static void findBodyTypeByIdTest() {
        Optional<BodyTypesEntity> bodyType = BodyTypesImplDao.getInstance().findById(3);
        System.out.println(bodyType);
    }

    public static void findAllBodyTypesTest() {
        List<BodyTypesEntity> bodyTypes = BodyTypesImplDao.getInstance().findAll();
        System.out.println(bodyTypes);
    }

    public static void savedBodyTypeTest() {
        BodyTypesImplDao bodyTypeDao = BodyTypesImplDao.getInstance();
        BodyTypesEntity bodyType = new BodyTypesEntity();
        bodyType.setBodyTypeName("station wagon");
        BodyTypesEntity savedBodyType = bodyTypeDao.save(bodyType);
        System.out.println(savedBodyType);
    }

    public static void updateBodyTypeTest() {
        BodyTypesImplDao bodyTypeDao = BodyTypesImplDao.getInstance();
        Optional<BodyTypesEntity> bodyType = bodyTypeDao.findById(5);
        System.out.println(bodyType);
        bodyType.ifPresent(bodyTypeEntity -> {
            bodyTypeEntity.setBodyTypeName("TEST1");
            bodyTypeDao.update(bodyTypeEntity);
        });
        System.out.println(bodyTypeDao.findById(5));
    }

    public static void deleteBodyTypeTest() {
        BodyTypesImplDao bodyTypeDao = BodyTypesImplDao.getInstance();
        boolean deleteResult = bodyTypeDao.delete(5);
        System.out.println(deleteResult);
    }
}
