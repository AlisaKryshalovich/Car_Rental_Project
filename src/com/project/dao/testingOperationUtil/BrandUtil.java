package com.project.dao.testingOperationUtil;

import com.project.dao.impl.BrandImplDao;
import com.project.entity.BrandEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class BrandUtil {

    public static void findBrandByIdTest() {
        Optional<BrandEntity> brand = BrandImplDao.getInstance().findById(2);
        System.out.println(brand);
    }

    public static void findAllBrandsTest() {
        List<BrandEntity> brands = BrandImplDao.getInstance().findAll();
        System.out.println(brands);
    }

    public static void savedBrandTest() {
        BrandImplDao brandDao = BrandImplDao.getInstance();
        BrandEntity brand = new BrandEntity();
        brand.setBrandName("test");
        BrandEntity savedBrand = brandDao.save(brand);
        System.out.println(savedBrand);
    }

    public static void updateBrandTest() {
        BrandImplDao brandDao = BrandImplDao.getInstance();
        Optional<BrandEntity> brandById = brandDao.findById(6);
        System.out.println(brandById);
        brandById.ifPresent(brandEntity -> {
            brandEntity.setBrandName("TEST1");
            brandDao.update(brandEntity);
        });
        System.out.println(brandDao.findById(6));
    }

    public static void deleteBrandTest() {
        BrandImplDao brandDao = BrandImplDao.getInstance();
        boolean deleteResult = brandDao.delete(6);
        System.out.println(deleteResult);
    }
}
