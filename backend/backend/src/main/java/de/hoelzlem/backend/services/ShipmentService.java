package de.hoelzlem.backend.services;

import de.hoelzlem.backend.dtos.CreateShipmentDto;
import de.hoelzlem.backend.entities.City;
import de.hoelzlem.backend.entities.FreightType;
import de.hoelzlem.backend.entities.Shipment;
import de.hoelzlem.backend.entities.ShipmentStatus;
import de.hoelzlem.backend.repositories.CityRepository;
import de.hoelzlem.backend.repositories.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final CityRepository cityRepository;

    private final DeliveryTimeService deliveryTimeService;

    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }


    public Shipment createShipment(CreateShipmentDto createShipmentDto) throws Exception {
        if(createShipmentDto.getOriginCityId() == createShipmentDto.getDestinationCityId()) {
            throw new Exception("Origin and destination city must be different");
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

            return shipmentRepository.save(Shipment.builder()
                    .originCity(originCity.get())
                    .destinationCity(destinationCity.get())
                    .freightType(freightType.get())
                    .estimatedDays(calculatedDeliveryTimeInDays)
                    .status(ShipmentStatus.IN_PROGRESS)
                    .build());
        } else {
           throw new Exception("Invalid input");
        }
    }

}
