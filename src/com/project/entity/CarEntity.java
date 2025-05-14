package com.project.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
public class CarEntity {

    private Integer carId;
    private BrandEntity brand;
    private ModelEntity model;
    private BodyTypeEntity bodyType;
    private Integer year;
    private BigDecimal engineVolume;
    private TransmissionEntity transmission;
    private EngineTypeEntity engineType;
    private CarStatusEntity carStatus;
}
