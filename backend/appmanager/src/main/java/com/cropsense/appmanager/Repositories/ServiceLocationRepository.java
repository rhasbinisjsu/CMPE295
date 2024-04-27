package com.cropsense.appmanager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropsense.appmanager.Entities.ServiceLocation;

import java.util.List;
import java.util.Optional;


@Repository
public interface ServiceLocationRepository extends JpaRepository <ServiceLocation, Long> {
    
    public List<ServiceLocation> findByOwnerTenantId(Long ownerId);

    public Optional<ServiceLocation> findByOwnerTenantIdAndLocName(Long ownerId, String LocName);

}
