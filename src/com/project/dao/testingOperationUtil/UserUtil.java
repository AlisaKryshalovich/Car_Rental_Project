package com.project.dao.testingOperationUtil;

import com.project.dao.impl.UserImplDao;
import com.project.entity.Role;
import com.project.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class UserUtil {

    public static void findUserByIdTest() {
        Optional<UserEntity> user = UserImplDao.getInstance().findById(3L);
        System.out.println(user);
    }

    public static void findAllUsersTest() {
        List<UserEntity> allUsers = UserImplDao.getInstance().findAll();
        System.out.println(allUsers);
    }

    public static void saveUserTest() { // 5
        UserImplDao userDao = UserImplDao.getInstance();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("test");
        userEntity.setLastName("test");
        userEntity.setAge(45);
        userEntity.setEmail("test@test.com");
        userEntity.setPassword("123456");
        userEntity.setRole(Role.ADMIN);
        UserEntity savedUser = userDao.save(userEntity);
        System.out.println(savedUser);
    }

    public static void updateUserTest() {
        UserImplDao userDao = UserImplDao.getInstance();
        Optional<UserEntity> user = userDao.findById(4L);
        System.out.println(user);

        user.ifPresent(userEntity -> {
            userEntity.setAge(38);
            userDao.update(userEntity);
        });
        System.out.println(userDao.findById(4L));
    }

    public static void deleteUserTest() {
        UserImplDao userDao = UserImplDao.getInstance();
        boolean deleteResult = false;
        deleteResult = userDao.delete(4L);
        System.out.println(deleteResult);
    }
}
