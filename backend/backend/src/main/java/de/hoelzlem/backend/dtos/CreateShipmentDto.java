package de.hoelzlem.backend.dtos;

import de.hoelzlem.backend.entities.City;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateShipmentDto {

    private long originCityId;
    private long destinationCityId;
    private String freightType;

}
