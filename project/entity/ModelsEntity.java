package com.project.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class ModelsEntity {

    private Integer modelId;
    private String modelName;
}
