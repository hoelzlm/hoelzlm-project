package de.hoelzlem.backend.repositories;

import de.hoelzlem.backend.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
