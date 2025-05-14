package com.project.dao.testingOperationUtil;

import com.project.dao.impl.EngineTypeImplDao;
import com.project.entity.EngineTypeEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class EngineTypeUtil {

    public static void findEngineTypeByIdTest() {
        Optional<EngineTypeEntity> engineType = EngineTypeImplDao.getInstance().findById(2);
        System.out.println(engineType);
    }

    public static void findAllEngineTypesTest() {
        List<EngineTypeEntity> engineTypes = EngineTypeImplDao.getInstance().findAll();
        System.out.println(engineTypes);
    }

    public static void savedEngineTypeTest() {
        EngineTypeImplDao engineTypeDao = EngineTypeImplDao.getInstance();
        EngineTypeEntity engineType = new EngineTypeEntity();
        engineType.setEngineTypeName("test");
        EngineTypeEntity savedEngineType = engineTypeDao.save(engineType);
        System.out.println(savedEngineType);
    }

    public static void updateEngineTypeTest() {
        EngineTypeImplDao engineTypeDao = EngineTypeImplDao.getInstance();
        Optional<EngineTypeEntity> engineTypeById = engineTypeDao.findById(3);
        System.out.println(engineTypeById);
        engineTypeById.ifPresent(engineTypeEntity -> {
            engineTypeEntity.setEngineTypeName("TEST1");
            engineTypeDao.update(engineTypeEntity);
        });
        System.out.println(engineTypeDao.findById(3));
    }

    public static void deleteEngineTypeTest() {
        EngineTypeImplDao engineTypeDao = EngineTypeImplDao.getInstance();
        boolean deleteResult = engineTypeDao.delete(3);
        System.out.println(deleteResult);
    }
}
