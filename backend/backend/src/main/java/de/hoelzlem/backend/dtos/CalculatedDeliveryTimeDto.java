package de.hoelzlem.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatedDeliveryTimeDto {
    private long deliveryTimeInDays;
}
