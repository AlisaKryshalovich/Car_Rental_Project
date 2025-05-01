package com.project.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class CarsEntity {

    private Integer carId;
    private BrandsEntity brand;
    private ModelsEntity model;
    private BodyTypesEntity bodyType;
    private Integer year;
    private BigDecimal engineVolume;
    private TransmissionsEntity transmission;
    private EngineTypesEntity engineType;
    private CarStatusEntity carStatus;
}
