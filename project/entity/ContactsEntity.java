package com.project.entity;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class ContactsEntity {

    private Long contactId;
    private UsersEntity user;
    private String email;
    private String phone;
}
