package de.hoelzlem.backend.services;

import de.hoelzlem.backend.entities.City;
import de.hoelzlem.backend.entities.FreightType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeliveryTimeService {

    private final Map<String, String> continentLookup = Map.of(
            "Germany", "Europe",
            "Netherlands", "Europe",
            "USA", "North America",
            "Canada", "North America",
            "Singapore", "Asia",
            "China", "Asia"
    );

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    /**
    * @param origin city of origin
     * @param destination city of destination
     * @return number of days it takes to ship between origin and destination
    */
    public int calculateDays(City origin, City destination, FreightType freightType) {

        // check same country
        if (origin.getCountry().equals(destination.getCountry()) && freightType == FreightType.AIR) {
            return 1;
        }

        if (origin.getCountry().equals(destination.getCountry()) && freightType == FreightType.SEA) {
            return 3;
        }

        // check same continent
        if (continentLookup.get(origin.getCountry()).equals(continentLookup.get(destination.getCountry())) && freightType == FreightType.AIR) {
            return this.getRandomNumber(2, 3);
        }

        if(continentLookup.get(origin.getCountry()).equals(continentLookup.get(destination.getCountry())) && freightType == FreightType.SEA) {
            return this.getRandomNumber(7, 10);
        }

        // case it is intercontinental air shipment
        if (freightType == FreightType.AIR) {
            return this.getRandomNumber(5, 7);
        }

        // case it is intercontinental sea shipment
        return this.getRandomNumber(20, 35);
    };

}
