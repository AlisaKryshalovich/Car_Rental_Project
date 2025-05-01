package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.ModelsImplDao;
import com.project.entity.ModelsEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class ModelsUtil {

    public static void findModelByIdTest() {
        Optional<ModelsEntity> model = ModelsImplDao.getInstance().findById(2);
        System.out.println(model);
    }

    public static void findAllModelsTest() {
        List<ModelsEntity> models = ModelsImplDao.getInstance().findAll();
        System.out.println(models);
    }

    public static void savedModelTest() {
        ModelsImplDao modelDao = ModelsImplDao.getInstance();
        ModelsEntity model = new ModelsEntity();
        model.setModelName("test");
        ModelsEntity savedModel = modelDao.save(model);
        System.out.println(savedModel);
    }

    public static void updateModelTest() {
        ModelsImplDao modelDao = ModelsImplDao.getInstance();
        Optional<ModelsEntity> modelById = modelDao.findById(5);
        System.out.println(modelById);
        modelById.ifPresent(modelEntity -> {
            modelEntity.setModelName("TEST1");
            modelDao.update(modelEntity);
        });
        System.out.println(modelDao.findById(5));
    }

    public static void deleteModelTest() {
        ModelsImplDao modelDao = ModelsImplDao.getInstance();
        boolean deleteResult = modelDao.delete(5);
        System.out.println(deleteResult);
    }
}
