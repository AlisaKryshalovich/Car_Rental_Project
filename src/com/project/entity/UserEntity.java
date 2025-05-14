package com.project.entity;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class UserEntity {

    private Long userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
//    private String passportNumber;
    private Role role;
}
