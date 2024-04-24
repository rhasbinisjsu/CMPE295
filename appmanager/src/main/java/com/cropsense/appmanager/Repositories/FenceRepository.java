package com.cropsense.appmanager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropsense.appmanager.Entities.Fence;

@Repository
public interface FenceRepository extends JpaRepository <Fence, Long> {
    
}
