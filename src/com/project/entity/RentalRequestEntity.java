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
public class RentalRequestEntity {

    private Integer requestId;
    private CarEntity car;
    private UserEntity user;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
    private RequestStatusEntity requestStatus;
}
