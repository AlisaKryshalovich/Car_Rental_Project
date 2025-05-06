package com.project.service;

import com.project.dao.impl.UserImplDao;
import com.project.dto.UserDto;

import java.util.List;
import static java.util.stream.Collectors.*;

public class UserService {

    private static volatile UserService INSTANCE;
    private final UserImplDao userDao = UserImplDao.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            synchronized (UserService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserService();
                }
            }
        }
        return INSTANCE;
    }

    public UserDto findById(Long id) {
        return userDao.findById(id)
                .map(userEntity -> new UserDto(
                        userEntity.getUserId(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getRole().getRoleName()
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(userEntity -> new UserDto(
                        userEntity.getUserId(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getRole().getRoleName()))
                .collect(toList());
    }
}
