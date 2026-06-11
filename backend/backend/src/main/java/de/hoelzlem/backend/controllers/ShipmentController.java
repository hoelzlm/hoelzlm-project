package de.hoelzlem.backend.controllers;


import de.hoelzlem.backend.dtos.CreateShipmentDto;
import de.hoelzlem.backend.entities.City;
import de.hoelzlem.backend.entities.FreightType;
import de.hoelzlem.backend.entities.Shipment;
import de.hoelzlem.backend.entities.ShipmentStatus;
import de.hoelzlem.backend.repositories.CityRepository;
import de.hoelzlem.backend.repositories.ShipmentRepository;
import de.hoelzlem.backend.services.DeliveryTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/shipments", produces = "application/json")
@RequiredArgsConstructor
public class ShipmentController {

    private final CityRepository cityRepository;

    private final ShipmentRepository shipmentRepository;

    private final DeliveryTimeService deliveryTimeService;

    @GetMapping
    List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

    @PostMapping
    ResponseEntity<Shipment> createShipment(@RequestBody CreateShipmentDto createShipmentDto) {

        // check if origin and destination are the same
        if(createShipmentDto.getOriginCityId() == createShipmentDto.getDestinationCityId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<FreightType> freightType = Optional.empty();

        Optional<City> originCity = cityRepository.findById(createShipmentDto.getOriginCityId());
        Optional<City> destinationCity = cityRepository.findById(createShipmentDto.getDestinationCityId());

        if(createShipmentDto.getFreightType().equals("AIR")) {
            freightType = Optional.of(FreightType.AIR);
        }

        if(createShipmentDto.getFreightType().equals("SEA")) {
            freightType = Optional.of(FreightType.SEA);
        }

        if (originCity.isPresent() && destinationCity.isPresent() && freightType.isPresent()) {

            int calculatedDeliveryTimeInDays = deliveryTimeService.calculateDays(originCity.get(), destinationCity.get(), freightType.get());

            Shipment shipment = shipmentRepository.save(Shipment.builder()
                    .originCity(originCity.get())
                    .destinationCity(destinationCity.get())
                    .freightType(freightType.get())
                    .estimatedDays(calculatedDeliveryTimeInDays)
                    .status(ShipmentStatus.IN_PROGRESS)
                    .build());

            return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
