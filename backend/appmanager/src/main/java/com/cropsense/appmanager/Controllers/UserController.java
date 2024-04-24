package com.cropsense.appmanager.Controllers;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.appmanager.Entities.User;
import com.cropsense.appmanager.Services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {

    @Autowired
    private UserService uService;
    
    /**
     * Create & persist a new user
     * @param uname
     * @param fname
     * @param lname
     * @param email
     * @param pNumber
     * @return ResponseEntity object with persisted user
     * @throws SQLException
     */
    @PostMapping(
        path = "/createUser",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> CreateUser(
        @RequestParam String uname,
        @RequestParam String fname,
        @RequestParam String lname,
        @RequestParam String email,
        @RequestParam String pNumber
    ) throws SQLException{

        User u = uService.createUser(uname, fname, lname, email, pNumber);
        ResponseEntity<?> response = new ResponseEntity<User>(u, HttpStatus.OK);

        return response;
    }


    /**
     * Fetch a persisted user by tenantId
     * @param tenantId
     * @return ResponseEntity with User
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchUserByTenantId",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUserByTenantId(@RequestParam long tenantId) throws SQLException {

        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByTenantId(tenantId);

        if (ou.isPresent()) {
            User u = ou.get();
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String reponseBody = "User with ID " + Long.toString(tenantId) + " is not found in the database"; 
            response = new ResponseEntity<String>(reponseBody, HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * Fetch persisted user by username
     * @param uname
     * @return ResponseEntity with User
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchUserByUsername",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUserByUsername(@RequestParam String uname) throws SQLException {

        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByUsername(uname);

        if (ou.isPresent()) {
            User u = ou.get();
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String reponseBody = "User with username " + uname + " is not found in the database"; 
            response = new ResponseEntity<String>(reponseBody, HttpStatus.BAD_REQUEST);
        }

        return response;

    }
    
    
    /**
     * Fetch a persisted user by email
     * @param email
     * @return ResponseEntity with User
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchUserByEmail",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUserByEmail(@RequestParam String email) throws SQLException {
        
        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByEmail(email);

        if (ou.isPresent()) {
            User u = ou.get();
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String responseBody = "User with email " + email + " is not found in the database";
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }
        
        return response;
    }


    /**
     * Delete a persisted user by tenantId
     * @param tenantId
     * @return ResponseEntity with String
     * @throws SQLException
     */
    @DeleteMapping(
        path = "/deleteUserByTenantId",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteUserByTenantId(@RequestParam long tenantId) throws SQLException {

        Optional<User> ou = uService.fetchUserByTenantId(tenantId);

        if (ou.isPresent()) {
            uService.deleteUserByTenantId(tenantId);
            String responseBody = "Successfully deleted user with ID " + Long.toString(tenantId);
            return new ResponseEntity<String>(responseBody, HttpStatus.OK);
        }
        else {
            String responseBody = "Cannot delete - User with ID " + Long.toString(tenantId) + " is not found";
            return new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }

    }
    

}
