package de.hoelzlem.backend.services;

import de.hoelzlem.backend.entities.City;
import de.hoelzlem.backend.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
