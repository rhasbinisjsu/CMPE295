package com.cropsense.app_server.Controllers;

import java.util.Optional;
import java.util.List;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cropsense.app_server.Entities.User;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.UserService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/CropSense/AppServer/UserController")
public class UserController {

    @Autowired
    private UserService uService;

    // Define and initialize logger
    private final AppLogger logger = new AppLogger(getClass().toString());
    

    /**
     * Persist a new user
     * @param newUser
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createUser",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createUser(@RequestBody User newUser) throws SQLException {

        logger.logInfoMsg("POST [ ENTER ] - Received request to create user");
        
        // define response entity
        ResponseEntity<?> response; 

        // check passed credentials are available
        String passedUname = newUser.getUname();
        String passedEmail = newUser.getEmail();
        String passedPnumber = newUser.getPnumber();

        logger.logInfoMsg("Checking credentials are available");

        if (uService.fetchUserByUsername(passedUname).isPresent()) {
            String resMsg = "Username is not available";
            logger.logErrorMsg("Failed to create user - username: " + passedUname + " is taken");
            response = new ResponseEntity<String>(resMsg, HttpStatus.BAD_REQUEST);
        }
        else if (uService.fetchUserByEmail(passedEmail).isPresent()) {
            String resMsg = "Email is not available";
            logger.logErrorMsg("Failed to create user - email: " + passedEmail + " is taken");
            response = new ResponseEntity<String>(resMsg, HttpStatus.BAD_REQUEST);
        }
        else if (uService.fetchUserByPnumber(passedPnumber).isPresent()) {
            String resMsg = "Phone number is not available";
            logger.logErrorMsg("Failed to create user - phone number: " + passedPnumber + " is taken");
            response = new ResponseEntity<String>(resMsg, HttpStatus.BAD_REQUEST);
        }
        else {
            // persist
            uService.createUser(newUser);
            logger.logInfoMsg("Successfully persisted the new user");
            response = new ResponseEntity<>(HttpStatus.CREATED);
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting create user request");
        return response;
    }


    /**
     * Validate login
     * @param uname
     * @param pswd
     * @return user ID
     * @throws SQLException
     */
    @GetMapping(
        path = "/login",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(
        @RequestParam String uname,
        @RequestParam String pswd
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request for login");

        // define response
        ResponseEntity<?> response;

        // initialize validated
        boolean validated = false;
    
        // check uname is valid
        Optional<User> ou = uService.fetchUserByUsername(uname);
        if (ou.isEmpty()) {
            logger.logWarnMsg("Failed to login - User not found");
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        else {
            validated = uService.validateLogin(uname, pswd);
            if (validated == true) {
                long userId = uService.fetchUserIdByUsername(uname);
                logger.logInfoMsg("User credentials pass - Login Success");
                response = new ResponseEntity<Long>(userId, HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                logger.logWarnMsg("Failed to login - Incorrect password passed");
            }
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request for login");
        return response;

    }
    

    /**
     * Fetch all persisted users
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchAllUsers",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<User>> fetchAllUsers() throws SQLException {

        logger.logInfoMsg("(ADMIN FUNCTION) GET [ ENTER ] - Received request to fetch all system user");

        List<User> uList = uService.fetchAllUsers();

        logger.logInfoMsg("(ADMIN FUNCTION) GET [ EXIT ] - Exiting request to fetch all system user");
        return new ResponseEntity<List<User>>(uList, HttpStatus.OK);

    }


    /**
     * Fetch a peristed user by username
     * @param username
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchUserByUsername",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUserByUsername(@RequestParam String username) throws SQLException {
        
        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch user with username:" + username);

        // define response
        ResponseEntity<?> response; 

        // fetch user
        Optional<User> ou = uService.fetchUserByUsername(username);
        if (ou.isPresent()) {
            User u = ou.get();
            logger.logInfoMsg("Success - User with username: " + username + " is found");
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            logger.logErrorMsg("Failed to fetch user with username: " + username + " - User does not exist");
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.logInfoMsg("GET [EXIT] - Exiting request to fetch user by username");
        return response;

    }


    /**
     * Change a user's password
     * @param userId
     * @param newPassword
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/changePassword",
        consumes = MediaType.TEXT_PLAIN_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> changePassword(
        @RequestParam long userId,
        @RequestBody String newPassword
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to change password for user: " + Long.toString(userId));
        uService.changeUserPassword(userId, newPassword);
        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to change password for user");
        return new ResponseEntity<>(HttpStatus.OK);

    }


    /**
     * Change a user's email
     * @param userId
     * @param newEmail
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/changeEmail",
        consumes = MediaType.TEXT_PLAIN_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> changeEmail(
        @RequestParam long userId,
        @RequestBody String newEmail
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to change email for user: " + Long.toString(userId));
        uService.changeUserEmail(userId, newEmail);
        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to change email for user");
        return new ResponseEntity<>(HttpStatus.OK);

    }


    /**
     * Change a user's phone number
     * @param userId
     * @param newPnumber
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/changePhoneNumber",
        consumes = MediaType.TEXT_PLAIN_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> changePhoneNumber(
        @RequestParam long userId,
        @RequestBody String newPnumber
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to change user phone number for user: " + Long.toString(userId));
        uService.changeUserPnumber(userId, newPnumber);
        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to change phone number for user");
        return new ResponseEntity<>(HttpStatus.OK);

    }


    /**
     * Delete a persisted user by username
     * @param uname
     * @return
     * @throws SQLException
     */
    @Transactional
    @DeleteMapping("/deleteUserByUsername")
    public ResponseEntity<HttpStatus> deleteUserByUsername(
        @RequestParam String uname
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete user with username: " + uname);
        uService.deleteUserByUsername(uname);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete user");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    
    @Transactional
    @DeleteMapping("/deleteUserById")
    public ResponseEntity<HttpStatus> deleteUserById(
        @RequestParam long id
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete user with ID: " + Long.toString(id));
        uService.deleteUserById(id);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete user");
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
