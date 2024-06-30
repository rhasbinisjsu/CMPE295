package com.cropsense.app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.FarmFence;

import java.util.Optional;


public interface FarmFenceRepository extends JpaRepository<FarmFence, Long> {

    Optional<FarmFence> findByFarmId(long farmId);
    void deleteByFarmId(long farmId);
    
}
