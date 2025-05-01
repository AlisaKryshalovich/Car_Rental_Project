package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.RolesImplDao;
import com.project.entity.RolesEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class RolesUtil {

    public static void findRoleByIdTest() {
        Optional<RolesEntity> role = RolesImplDao.getInstance().findById(2);
        System.out.println(role);
    }

    public static void findAllRolesTest() {
        List<RolesEntity> allRoles = RolesImplDao.getInstance().findAll();
        System.out.println(allRoles);
    }

    public static void savedRoleTest() {
        RolesImplDao roleDao = RolesImplDao.getInstance();
        RolesEntity role = new RolesEntity();
        role.setRoleName("test5");
        RolesEntity savedUser = roleDao.save(role);
        System.out.println(savedUser);
    }

    public static void updateRoleTest() {
        RolesImplDao roleDao = RolesImplDao.getInstance();
        Optional<RolesEntity> maybeRole = roleDao.findById(8);
        System.out.println(maybeRole);
        maybeRole.ifPresent(roleEntity -> {
            roleEntity.setRoleName("гость");
            roleDao.update(roleEntity);
        });
        System.out.println(roleDao.findById(8));
    }

    public static void deleteRoleTest() {
        RolesImplDao roleDao = RolesImplDao.getInstance();
        boolean deleteResult = roleDao.delete(11);
        System.out.println(deleteResult);
    }
}
