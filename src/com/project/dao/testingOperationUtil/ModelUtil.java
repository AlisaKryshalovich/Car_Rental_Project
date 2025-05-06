package com.project.dao.testingOperationUtil;

import com.project.dao.impl.ModelImplDao;
import com.project.entity.ModelEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class ModelUtil {

    public static void findModelByIdTest() {
        Optional<ModelEntity> model = ModelImplDao.getInstance().findById(2);
        System.out.println(model);
    }

    public static void findAllModelsTest() {
        List<ModelEntity> models = ModelImplDao.getInstance().findAll();
        System.out.println(models);
    }

    public static void savedModelTest() {
        ModelImplDao modelDao = ModelImplDao.getInstance();
        ModelEntity model = new ModelEntity();
        model.setModelName("test");
        ModelEntity savedModel = modelDao.save(model);
        System.out.println(savedModel);
    }

    public static void updateModelTest() {
        ModelImplDao modelDao = ModelImplDao.getInstance();
        Optional<ModelEntity> modelById = modelDao.findById(5);
        System.out.println(modelById);
        modelById.ifPresent(modelEntity -> {
            modelEntity.setModelName("TEST1");
            modelDao.update(modelEntity);
        });
        System.out.println(modelDao.findById(5));
    }

    public static void deleteModelTest() {
        ModelImplDao modelDao = ModelImplDao.getInstance();
        boolean deleteResult = modelDao.delete(5);
        System.out.println(deleteResult);
    }
}
