package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.BrandsImplDao;
import com.project.entity.BrandsEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class BrandsUtil {

    public static void findBrandByIdTest() {
        Optional<BrandsEntity> brand = BrandsImplDao.getInstance().findById(2);
        System.out.println(brand);
    }

    public static void findAllBrandsTest() {
        List<BrandsEntity> brands = BrandsImplDao.getInstance().findAll();
        System.out.println(brands);
    }

    public static void savedBrandTest() {
        BrandsImplDao brandDao = BrandsImplDao.getInstance();
        BrandsEntity brand = new BrandsEntity();
        brand.setBrandName("test");
        BrandsEntity savedBrand = brandDao.save(brand);
        System.out.println(savedBrand);
    }

    public static void updateBrandTest() {
        BrandsImplDao brandDao = BrandsImplDao.getInstance();
        Optional<BrandsEntity> brandById = brandDao.findById(6);
        System.out.println(brandById);
        brandById.ifPresent(brandEntity -> {
            brandEntity.setBrandName("TEST1");
            brandDao.update(brandEntity);
        });
        System.out.println(brandDao.findById(6));
    }

    public static void deleteBrandTest() {
        BrandsImplDao brandDao = BrandsImplDao.getInstance();
        boolean deleteResult = brandDao.delete(6);
        System.out.println(deleteResult);
    }
}
