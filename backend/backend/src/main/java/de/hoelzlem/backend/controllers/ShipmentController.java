package de.hoelzlem.backend.controllers;


import de.hoelzlem.backend.dtos.CalculatedDeliveryTimeDto;
import de.hoelzlem.backend.dtos.CreateShipmentDto;
import de.hoelzlem.backend.entities.Shipment;
import de.hoelzlem.backend.services.DeliveryTimeService;
import de.hoelzlem.backend.services.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/shipments", produces = "application/json")
@RequiredArgsConstructor
public class ShipmentController {

private final ShipmentService shipmentService;
private final DeliveryTimeService deliveryTimeService;

    @GetMapping
    List<Shipment> getShipments() {
        return shipmentService.getShipments();
    }

    @PostMapping
    ResponseEntity<Shipment> createShipment(@RequestBody CreateShipmentDto createShipmentDto) {
        try {
            Shipment shipment = shipmentService.createShipment(createShipmentDto);
            return new ResponseEntity<>(shipment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/calculated")
    ResponseEntity<CalculatedDeliveryTimeDto> getCalculatedDeliveryTime(
            @RequestParam("originCityId") long originCityId,
            @RequestParam("destinationCityId") long destinationCityId,
            @RequestParam("freightType") String freightType
    ) {
        try {
            int calculatedDays = deliveryTimeService.calculateDays(originCityId, destinationCityId, freightType);
            return new ResponseEntity<>(new CalculatedDeliveryTimeDto(calculatedDays), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
