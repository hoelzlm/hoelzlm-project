package de.hoelzlem.backend.controllers;

import de.hoelzlem.backend.entities.City;
import de.hoelzlem.backend.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cities", produces = "application/json")
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping()
    List<City> getCities() {
        return cityRepository.findAll();
    }
}
