package com.project.entity;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class CarStatusEntity {

    private Integer statusId;
    private String statusName;
}
