package com.project.entity;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class BodyTypesEntity {

    private Integer bodyTypeId;
    private String bodyTypeName;
}
