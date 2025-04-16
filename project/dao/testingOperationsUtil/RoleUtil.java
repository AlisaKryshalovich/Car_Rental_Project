package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.RoleDaoImpl;
import com.project.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public final class RoleUtil {
    private RoleUtil() {}

    public static void deleteRoleTest() {
        RoleDaoImpl roleDao = RoleDaoImpl.getInstance();
        boolean deleteResult = roleDao.delete(11);
        System.out.println(deleteResult);
    }

    public static void updateRoleTest() {
        RoleDaoImpl roleDao = RoleDaoImpl.getInstance();
        Optional<RoleEntity> maybeRole = roleDao.findById(8);
        System.out.println(maybeRole);
        maybeRole.ifPresent(roleEntity -> {
            roleEntity.setRoleName("гость");
            roleDao.update(roleEntity);
        });
    }

    public static void savedRoleTest() {
        RoleDaoImpl roleDao = RoleDaoImpl.getInstance();
        RoleEntity role = new RoleEntity();
        role.setRoleName("test5");
        RoleEntity savedUser = roleDao.save(role);
        System.out.println(savedUser);
    }

    public static void getRoleByIdTest() {
        Optional<RoleEntity> role = RoleDaoImpl.getInstance().findById(2);
        System.out.println(role);
    }

    public static void getAllRolesTest() {
        List<RoleEntity> allRoles = RoleDaoImpl.getInstance().findAll();
        System.out.println(allRoles);
    }
}
