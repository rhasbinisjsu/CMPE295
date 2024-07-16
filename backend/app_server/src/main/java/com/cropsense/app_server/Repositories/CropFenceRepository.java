package com.cropsense.app_server.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.CropFence;

public interface CropFenceRepository extends JpaRepository<CropFence, Long> {

    Optional<CropFence> findByCropId(long cropId);
    void deleteByCropId(long cropId);

}
