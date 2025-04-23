package com.project.entity;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UsersEntity {

    private Long userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String password;
    private String passportNumber;
    private RoleEntity role;
}
