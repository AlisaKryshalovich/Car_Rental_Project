package com.project.dto;

import com.project.entity.RoleEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String role;
}
