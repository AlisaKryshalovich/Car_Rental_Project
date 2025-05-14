package com.project.service;

import com.project.dao.impl.UserImplDao;
import com.project.dto.CreateUserDto;
import com.project.dto.UserDto;
import com.project.entity.UserEntity;
import com.project.exception.ValidationException;
import com.project.mapper.CreateUserMapper;
import com.project.mapper.UserMapper;
import com.project.validator.CreateUserValidator;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserImplDao userDao = UserImplDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public Optional<CreateUserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);
    }

    public Long create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        UserEntity userEntity = createUserMapper.mapFrom(userDto);
        userDao.save(userEntity);
        return userEntity.getUserId();
    }

    public UserDto findById(Long id) {
        return userDao.findById(id)
                .map(userEntity -> new UserDto(
                        userEntity.getUserId(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getRole().name()
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(userEntity -> new UserDto(
                        userEntity.getUserId(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getRole().name()))
                .collect(toList());
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
