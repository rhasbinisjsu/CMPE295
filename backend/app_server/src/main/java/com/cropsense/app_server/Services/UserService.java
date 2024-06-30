package com.cropsense.app_server.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.app_server.Entities.User;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository uRepo;

    // define and initialize logger
    private final AppLogger logger = new AppLogger(getClass().toString());

    /**
     * Persist a new user
     * @param newUser
     * @return persisted user object
     */
    public User createUser(User newUser) throws SQLException {
        
        logger.logInfoMsg("Persisting new user");

        try {
            return uRepo.save(newUser);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to persist the new user" + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Validated inputed password for user by username
     * @param uname
     * @param pswd
     * @return boolean
     * @throws SQLException
     */
    public boolean validateLogin(String uname, String pswd) throws SQLException {
        
        logger.logInfoMsg("Validating login credentials");

        // initialize validated
        boolean validated = false;
        
        try{
            // fetch the user 
            User u = uRepo.findByUname(uname).get();
            // get the fetched user's password
            String actualPswd = u.getPswd();

            // compare entered pswd to persisted pswd
            if (actualPswd.equals(pswd)) {
                validated = true;
            }

            logger.logInfoMsg("Validated = " + validated);

            return validated;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to run authentication block" + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch user ID by username
     * @param uname
     * @return
     * @throws SQLException
     */
    public long fetchUserIdByUsername(String uname) throws SQLException {
        
        logger.logInfoMsg("Fetching user ID for username: " + uname);

        try {
            User u = uRepo.findByUname(uname).get();
            long userId = u.getId();
            logger.logInfoMsg("User ID for username '" + uname + "' is " + Long.toString(userId));
            return userId;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch user ID for user with username: " + uname + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Fetch all persisted users
     * @return List of persisted users
     * @throws SQLException
     */
    public List<User> fetchAllUsers() throws SQLException {
        
        logger.logInfoMsg("Fetching all system users");
        
        try {
            return uRepo.findAll();
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch system users " + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }


    /**
     * Fetch a persisted user by username
     * @param uname
     * @return
     * @throws SQLException
     */
    public Optional<User> fetchUserByUsername(String uname) throws SQLException {
        
        logger.logInfoMsg("Fetching User object for username: " + uname);
        
        try {
            Optional<User> ou = uRepo.findByUname(uname);

            if(ou.isPresent()) {
                logger.logInfoMsg("Found User with username '" + uname + "'");
            }
            else {
                logger.logInfoMsg("Username '" + uname + "' does not exist");
            }

            return ou;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch user" + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }

    
    /**
     * Fetch a persisted user by email
     * @param email
     * @return
     * @throws SQLException
     */
    public Optional<User> fetchUserByEmail(String email) throws SQLException {

        logger.logInfoMsg("Fetching User object for email: " + email);
        
        try {
            Optional<User> ou = uRepo.findByEmail(email);
            
            if(ou.isPresent()) {
                logger.logInfoMsg("Found user with email: " + email);
            }
            else {
                logger.logInfoMsg("User with email '" + email + "' does not exist");
            }

            return ou;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch user by email" + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Fetch a persisted user by phone number
     * @param pnumber
     * @return
     * @throws SQLException
     */
    public Optional<User> fetchUserByPnumber(String pnumber) throws SQLException {
        
        logger.logInfoMsg("Fetching User object for phone number: " + pnumber);
        
        try {
            Optional<User> ou = uRepo.findByPnumber(pnumber);
            
            if (ou.isPresent()) {
                logger.logInfoMsg("Found user with phone number: " + pnumber);
            }
            else {
                logger.logInfoMsg("User with phone number '" + pnumber + "' does not exist");
            }

            return ou;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to fetch user by phone number" + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Updated a persisted user's password by ID
     * @param id
     * @param newPswd
     * @return
     * @throws SQLException
     */
    public User changeUserPassword(long id, String newPswd) throws SQLException {
        
        logger.logInfoMsg("Changing password for User with ID: " + Long.toString(id));

        try {
            User u = uRepo.findById(id).get();
            u.setPswd(newPswd);
            uRepo.save(u);
            logger.logInfoMsg("Updated password for user");
            return u;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to update password" + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Update a persisted user's email by ID
     * @param id
     * @param newEmail
     * @return
     * @throws SQLException
     */
    public User changeUserEmail(long id, String newEmail) throws SQLException {
        
        logger.logInfoMsg("Changing email for user with ID: " + Long.toString(id));
        
        try {
            User u = uRepo.findById(id).get();
            u.setEmail(newEmail);
            uRepo.save(u);
            logger.logInfoMsg("Updated email for user");
            return u;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to update email" + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Update a persisted user's phone number by ID
     * @param id
     * @param newNumber
     * @return
     * @throws SQLException
     */
    public User changeUserPnumber(long id, String newNumber) throws SQLException {

        logger.logInfoMsg("Changing phone number for user with ID: " + Long.toString(id));

        try {
            User u = uRepo.findById(id).get();
            u.setPnumber(newNumber);
            uRepo.save(u);
            logger.logInfoMsg("Updated phone number for user");
            return u;
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to update phone number" + "\n Reason: " + e.getMessage());
            throw new SQLException();
        }

    }


    /**
     * Delete a persisted user by username
     * @param uname
     * @throws SQLException
     */
    public void deleteUserByUsername(String uname) throws SQLException {
        
        logger.logInfoMsg("Deleting user with username: " + uname);
        
        try {
            uRepo.deleteByUname(uname);
            logger.logInfoMsg("Successfully deleted user with username: " + uname);
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to delete user by username" + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
    }


    /**
     * Delete a persister user by id
     * @param Id
     * @throws SQLException
     */
    public void deleteUserById(long Id) throws SQLException {

        logger.logInfoMsg("Deleting user with ID: " + Long.toString(Id));

        try {
            uRepo.deleteById(Id);
            logger.logInfoMsg("Successfully deleted user with ID: " + Long.toString(Id));
        }
        catch (Exception e) {
            logger.logWarnMsg("Failed to delete user by ID" + " \n Reason: " + e.getMessage());
            throw new SQLException();
        }
        
    }

}
