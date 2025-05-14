package com.project.mapper;

import com.project.dto.CreateUserDto;
import com.project.entity.Role;
import com.project.entity.UserEntity;

public class CreateUserMapper implements Mapper<CreateUserDto, UserEntity> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    private CreateUserMapper() {}

    @Override
    public UserEntity mapFrom(CreateUserDto object) {
        return UserEntity.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .age(Integer.parseInt(object.getAge()))
                .email(object.getEmail())
                .password(object.getPassword())
                .role(Role.valueOf(object.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
