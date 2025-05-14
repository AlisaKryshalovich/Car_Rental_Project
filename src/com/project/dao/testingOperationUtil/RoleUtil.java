//package com.project.dao.testingOperationUtil;
//
//import com.project.dao.impl.RoleImplDao;
//import com.project.entity.Role;
//import lombok.experimental.UtilityClass;
//
//import java.util.List;
//import java.util.Optional;
//
//@UtilityClass
//public class RoleUtil {
//
//    public static void findRoleByIdTest() {
//        Optional<Role> role = RoleImplDao.getInstance().findById(2);
//        System.out.println(role);
//    }
//
//    public static void findAllRolesTest() {
//        List<Role> allRoles = RoleImplDao.getInstance().findAll();
//        System.out.println(allRoles);
//    }
//
//    public static void savedRoleTest() {
//        RoleImplDao roleDao = RoleImplDao.getInstance();
//        Role role = new Role();
//        role.setRoleName("test5");
//        Role savedUser = roleDao.save(role);
//        System.out.println(savedUser);
//    }
//
//    public static void updateRoleTest() {
//        RoleImplDao roleDao = RoleImplDao.getInstance();
//        Optional<Role> maybeRole = roleDao.findById(8);
//        System.out.println(maybeRole);
//        maybeRole.ifPresent(roleEntity -> {
//            roleEntity.setRoleName("гость");
//            roleDao.update(roleEntity);
//        });
//        System.out.println(roleDao.findById(8));
//    }
//
//    public static void deleteRoleTest() {
//        RoleImplDao roleDao = RoleImplDao.getInstance();
//        boolean deleteResult = roleDao.delete(11);
//        System.out.println(deleteResult);
//    }
//}
