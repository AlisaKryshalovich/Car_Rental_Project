package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.UsersDaoImpl;
import com.project.entity.RoleEntity;
import com.project.entity.UsersEntity;

import java.util.List;
import java.util.Optional;

public final class UsersUtil {
    private UsersUtil() {}

    public static void findUserByIdTest() {
        Optional<UsersEntity> user = UsersDaoImpl.getInstance().findById(3L);
        System.out.println(user);
    }

    public static void findAllUsersTest() {
        List<UsersEntity> allUsers = UsersDaoImpl.getInstance().findAll();
        System.out.println(allUsers);
    }

    public static void updateUserTest() {
        UsersDaoImpl userDao = UsersDaoImpl.getInstance();
        Optional<UsersEntity> user = userDao.findById(6L);
        System.out.println(user);

        user.ifPresent(userEntity -> {
            userEntity.setAge(38);
            userDao.update(userEntity);
        });
    }

    public static void saveUserTest() { // 5
        UsersDaoImpl userDao = UsersDaoImpl.getInstance();
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setFirstName("test");
        usersEntity.setLastName("test");
        usersEntity.setAge(45);
        usersEntity.setPassword("123456");
        usersEntity.setPassportNumber("123456");
        usersEntity.setRole(new RoleEntity(2, "USER"));
        UsersEntity savedUser = userDao.save(usersEntity);
        System.out.println(savedUser);
    }

    public static void deleteUserTest() {
        UsersDaoImpl userDao = UsersDaoImpl.getInstance();
        boolean deleteResult = false;
        deleteResult = userDao.delete(6L);
        System.out.println(deleteResult);
    }
}
