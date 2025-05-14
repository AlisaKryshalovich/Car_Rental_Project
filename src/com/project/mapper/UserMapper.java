package com.project.mapper;

import com.project.dto.CreateUserDto;
import com.project.entity.UserEntity;

public class UserMapper implements Mapper<UserEntity, CreateUserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    private UserMapper() {}

    @Override
    public CreateUserDto mapFrom(UserEntity userEntity) {
        return CreateUserDto.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .age(String.valueOf(userEntity.getAge()))
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole().name())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
