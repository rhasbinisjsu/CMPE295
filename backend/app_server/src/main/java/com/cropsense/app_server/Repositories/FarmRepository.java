package com.cropsense.app_server.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.Farm;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    
    Optional<Farm> findById(long id);
    List<Farm> findByOwnerId(long ownerId);
    Optional<Farm> findByOwnerIdAndName(long ownerId, String name);

}
