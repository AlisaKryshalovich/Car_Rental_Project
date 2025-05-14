package com.project.entity;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@ToString
@EqualsAndHashCode
public class RequestStatusEntity {

    private Integer statusId;
    private String statusName;
}
