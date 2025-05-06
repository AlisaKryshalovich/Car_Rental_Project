package com.project.dao.testingOperationUtil;

import com.project.dao.impl.BodyTypeImplDao;
import com.project.entity.BodyTypeEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class BodyTypeUtil {

    public static void findBodyTypeByIdTest() {
        Optional<BodyTypeEntity> bodyType = BodyTypeImplDao.getInstance().findById(3);
        System.out.println(bodyType);
    }

    public static void findAllBodyTypesTest() {
        List<BodyTypeEntity> bodyTypes = BodyTypeImplDao.getInstance().findAll();
        System.out.println(bodyTypes);
    }

    public static void savedBodyTypeTest() {
        BodyTypeImplDao bodyTypeDao = BodyTypeImplDao.getInstance();
        BodyTypeEntity bodyType = new BodyTypeEntity();
        bodyType.setBodyTypeName("station wagon");
        BodyTypeEntity savedBodyType = bodyTypeDao.save(bodyType);
        System.out.println(savedBodyType);
    }

    public static void updateBodyTypeTest() {
        BodyTypeImplDao bodyTypeDao = BodyTypeImplDao.getInstance();
        Optional<BodyTypeEntity> bodyType = bodyTypeDao.findById(5);
        System.out.println(bodyType);
        bodyType.ifPresent(bodyTypeEntity -> {
            bodyTypeEntity.setBodyTypeName("TEST1");
            bodyTypeDao.update(bodyTypeEntity);
        });
        System.out.println(bodyTypeDao.findById(5));
    }

    public static void deleteBodyTypeTest() {
        BodyTypeImplDao bodyTypeDao = BodyTypeImplDao.getInstance();
        boolean deleteResult = bodyTypeDao.delete(5);
        System.out.println(deleteResult);
    }
}
