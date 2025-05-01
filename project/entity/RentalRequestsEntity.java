package com.project.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@ToString
@EqualsAndHashCode
public class RentalRequestsEntity {

    private Integer requestId;
    private CarsEntity car;
    private UsersEntity user;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
    private RequestStatusEntity requestStatus;
}
