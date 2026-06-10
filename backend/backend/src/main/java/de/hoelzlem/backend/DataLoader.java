package de.hoelzlem.backend;

import de.hoelzlem.backend.entities.City;
import de.hoelzlem.backend.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Autowired
    CityRepository cityRepository;

    List<City> newCities = List.of(
            City.builder().name("Hamburg").country("Germany").build(),
            City.builder().name("Rotterdam").country("Netherlands").build(),
            City.builder().name("Shanghai").country("China").build(),
            City.builder().name("New York").country("USA").build(),
            City.builder().name("Singapore").country("Singapore").build()
    );

    @Override
    public void run(String... args) throws Exception {

        List<City> cities = cityRepository.findAll();

        if (cities.isEmpty()) {
            cityRepository.saveAll(newCities);
        }
    }
}
