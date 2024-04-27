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
     * @param pswd
     * @param fname
     * @param lname
     * @param email
     * @param pNumber
     * @return the presisted user object
     * @throws SQLException
     */
    public User createUser(
        String uname,
        String pswd,
        String fname,
        String lname,
        String email,
        String pNumber
    ) throws SQLException {

        User u = new User();
        u.setUsername(uname);
        u.setPswd(pswd);
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
     * @return persited user optional object
     * @throws SQLException
     */
    public Optional<User> fetchUserByEmail(String email) throws SQLException {

        Optional<User> ou = userRepo.findByEmail(email);
        return ou;

    }


    /**
     * Fetch a persisted user by phone number
     * @param pNumber
     * @return
     * @throws SQLException
     */
    public Optional<User> fetchUserByPnumber(String pNumber) throws SQLException {

        Optional<User> ou = userRepo.findByPnumber(pNumber);
        return ou;

    }


    /**
     * Change persisted user's first name
     * @param id
     * @param newFname
     * @return
     * @throws SQLException
     */
    public User updateUserFnameById(long id, String newFname) throws SQLException {
        
        User u = userRepo.findById(id).get();
        u.setFname(newFname);
        userRepo.save(u);

        return u;

    }


    /**
     * Change persisted user's last name
     * @param id
     * @param newLname
     * @return
     * @throws SQLException
     */
    public User updateUserLnameById(long id, String newLname) throws SQLException {

        User u = userRepo.findById(id).get();
        u.setLname(newLname);
        userRepo.save(u);

        return u;
    }


    /**
     * Change persisted user's username
     * @param id
     * @param newUname
     * @return
     * @throws SQLException
     */
    public User updateUserUnameById(long id, String newUname) throws SQLException {

        User u = userRepo.findById(id).get();
        u.setUsername(newUname);
        userRepo.save(u);

        return u;
    }


    /**
     * Change persisted user's password
     * @param id
     * @param newPswd
     * @return
     * @throws SQLException
     */
    public User updateUserPasswordById(long id, String newPswd) throws SQLException {

        User u = userRepo.findById(id).get();
        u.setPswd(newPswd);
        userRepo.save(u);

        return u;
    }


    /**
     * Change persisted user's email
     * @param id
     * @param newEmail
     * @return
     * @throws SQLException
     */
    public User updateUserEmailById(long id, String newEmail) throws SQLException {

        User u = userRepo.findById(id).get();
        u.setEmail(newEmail);
        userRepo.save(u);

        return u;
    }


    /**
     * Change persisted user's phone number
     * @param id
     * @param newPnumber
     * @return
     * @throws SQLException
     */
    public User updateUserPnumberById(long id, String newPnumber) throws SQLException {

        User u = userRepo.findById(id).get();
        u.setPNumber(newPnumber);
        userRepo.save(u);

        return u;
    }


    /**
     * Delete a persisted user by tenantId
     * @param id
     * @throws SQLException
     */
    public void deleteUserByTenantId(long id) throws SQLException {

        Optional<User> u = userRepo.findById(id);
        
        if (u.isPresent()) {
            userRepo.deleteById(id);
        }

    }

    
}
