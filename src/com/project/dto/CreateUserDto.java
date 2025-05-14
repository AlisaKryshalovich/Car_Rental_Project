package com.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CreateUserDto {

        Long userId;
        String firstName;
        String lastName;
        String age;
        String email;
        String password;
        String role;
}
