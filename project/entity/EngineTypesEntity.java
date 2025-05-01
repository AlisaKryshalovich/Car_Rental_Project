package com.project.entity;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class EngineTypesEntity {

    private Integer engineTypeId;
    private String engineTypeName;
}
