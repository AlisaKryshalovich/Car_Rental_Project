package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.EngineTypesImplDao;
import com.project.entity.EngineTypesEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class EngineTypesUtil {

    public static void findEngineTypeByIdTest() {
        Optional<EngineTypesEntity> engineType = EngineTypesImplDao.getInstance().findById(2);
        System.out.println(engineType);
    }

    public static void findAllEngineTypesTest() {
        List<EngineTypesEntity> engineTypes = EngineTypesImplDao.getInstance().findAll();
        System.out.println(engineTypes);
    }

    public static void savedEngineTypeTest() {
        EngineTypesImplDao engineTypeDao = EngineTypesImplDao.getInstance();
        EngineTypesEntity engineType = new EngineTypesEntity();
        engineType.setEngineTypeName("test");
        EngineTypesEntity savedEngineType = engineTypeDao.save(engineType);
        System.out.println(savedEngineType);
    }

    public static void updateEngineTypeTest() {
        EngineTypesImplDao engineTypeDao = EngineTypesImplDao.getInstance();
        Optional<EngineTypesEntity> engineTypeById = engineTypeDao.findById(3);
        System.out.println(engineTypeById);
        engineTypeById.ifPresent(engineTypeEntity -> {
            engineTypeEntity.setEngineTypeName("TEST1");
            engineTypeDao.update(engineTypeEntity);
        });
        System.out.println(engineTypeDao.findById(3));
    }

    public static void deleteEngineTypeTest() {
        EngineTypesImplDao engineTypeDao = EngineTypesImplDao.getInstance();
        boolean deleteResult = engineTypeDao.delete(3);
        System.out.println(deleteResult);
    }
}
