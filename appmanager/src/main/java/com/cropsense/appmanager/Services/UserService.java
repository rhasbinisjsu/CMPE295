package com.cropsense.appmanager.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.appmanager.Entities.User;
import com.cropsense.appmanager.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    /**
     * Creates & persists a new user in db
     * @param uname
     * @param fname
     * @param lname
     * @param email
     * @param pNumber
     * @return the presisted user object
     * @throws SQLException
     */
    public User createUser(
        String uname,
        String fname,
        String lname,
        String email,
        String pNumber
    ) throws SQLException {

        User u = new User();
        u.setUsername(uname);
        u.setFname(fname);
        u.setLname(lname);
        u.setEmail(email);
        u.setPNumber(pNumber);

        userRepo.save(u);
        return u;

    }

    
    /**
     * Fetch all users persisted in the databse
     * @return List of persisted users
     * @throws SQLException
     */
    public List<User> fetchAllUsers() throws SQLException{
        
        List<User> uList = userRepo.findAll();
        return uList;

    }


    /**
     * Fetch a persisted user by tenantId
     * @param id
     * @return peristsed user optional object
     * @throws SQLException
     */
    public Optional<User> fetchUserByTenantId(long id) throws SQLException{
        
        Optional<User> ou = userRepo.findById(id);
        return ou;

    }


    /**
     * Fetch a persisted user by username
     * @param uname
     * @return persisted user optional object
     * @throws SQLException
     */
    public Optional<User> fetchUserByUsername(String uname) throws SQLException{

        Optional<User> ou = userRepo.findByUsername(uname);
        return ou;

    }
    

    /**
     * Fetch a persisted user by email
     * @param email
     * @return
     */
    /**
     * Fetch a persisted user by email
     * @param email
     * @return persited user optional object
     * @throws SQLException
     */
    public Optional<User> fetchUserByEmail(String email) throws SQLException {

        Optional<User> ou = userRepo.findByEmail(email);
        return ou;

    }


    /**
     * Delete a persisted user by tenantId
     * @param id
     * @throws SQLException
     */
    public void deleteUserByTenantId(long id) throws SQLException {

        Optional<User> u = userRepo.findById(id);
        
        if (u.isPresent()) {
            userRepo.deleteById(null);
        }

    }

    
}
