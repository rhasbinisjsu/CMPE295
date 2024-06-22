package com.cropsense.app_server.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    
    Optional<Drone> findByOwnerIdAndDroneName(long ownerId, String droneName);
    List<Drone> findByOwnerId(long ownerId);
    List<Drone> findByInService(boolean inService);
    List<Drone> findByDocked(boolean docked);

}
