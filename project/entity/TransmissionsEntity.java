package com.project.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class TransmissionsEntity {

    private Integer transmissionId;
    private String transmissionTypeName;
}
