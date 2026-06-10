package de.hoelzlem.backend.repositories;

import de.hoelzlem.backend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
}
