package com.cropsense.appmanager.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropsense.appmanager.Entities.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    
    /**
     * fetch user by username
     * @return
     */
    public Optional<User> findByUsername(String username);

    /**
     * fetch user by email
     * @return
     */
    public Optional<User> findByEmail(String email);

    /**
     * fetch user by phone number
     * @param pNumber
     * @return
     */
    public Optional<User> findByPnumber(String pNumber);
    

}
