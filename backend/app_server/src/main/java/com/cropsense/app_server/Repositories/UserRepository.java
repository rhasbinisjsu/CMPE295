package com.cropsense.app_server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropsense.app_server.Entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUname(String uname);
    Optional<User> findByEmail(String email);
    Optional<User> findByPnumber(String pnumber);
    void deleteByUname(String uname);

}
