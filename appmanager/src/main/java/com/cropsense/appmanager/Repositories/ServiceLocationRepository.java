package com.cropsense.appmanager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropsense.appmanager.Entities.ServiceLocation;

@Repository
public interface ServiceLocationRepository extends JpaRepository <ServiceLocation, Long> {
    
    

}
