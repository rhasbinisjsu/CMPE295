package com.cropsense.appmanager.Controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.cropsense.appmanager.Entities.User;
import com.cropsense.appmanager.Services.UserService;

@RestController
@RequestMapping("/appManager/userController")
public class UserController {

    @Autowired
    private UserService uService;
    
    /**
     * Create & persist a new user
     * @param uname
     * @param pswd
     * @param fname
     * @param lname
     * @param email
     * @param pnumber
     * @return ResponseEntity object with persisted user
     * @throws SQLException
     */
    @PostMapping(
        path = "/createUser",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> CreateUser(
        @RequestParam String uname,
        @RequestParam String pswd,
        @RequestParam String fname,
        @RequestParam String lname,
        @RequestParam String email,
        @RequestParam String pnumber
    ) throws SQLException{

        ResponseEntity<?> response;

        // check username
        boolean checkUsername = uService.fetchUserByUsername(uname).isEmpty();
        // check email
        boolean checkEmail = uService.fetchUserByEmail(email).isEmpty();
        // check phone number
        boolean checkPNumber = uService.fetchUserByPnumber(pnumber).isEmpty();

        if (checkUsername == false) {
            String responseBody = "Username is taken";
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }
        else if (checkEmail == false) {
            String responseBody = "Email is taken";
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }
        else if (checkPNumber == false) {
            String responseBody = "Phone number is taken";
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }
        else {
            User u = uService.createUser(uname, pswd, fname, lname, email, pnumber);
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }

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
    public ResponseEntity<?> fetchAllUsers() throws SQLException {
        
        ResponseEntity<?> response;
        List<User> userList = uService.fetchAllUsers();
        response = new ResponseEntity<List<User>>(userList,HttpStatus.OK);
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
        produces = MediaType.APPLICATION_JSON_VALUE
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
     * Change persisted user's first name
     * @param id
     * @param fname
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateUserFnameById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserFnameById(
        @RequestParam long id, 
        @RequestParam String fname
    ) throws SQLException {
        
        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByTenantId(id);

        if (ou.isPresent()) {
            User u = uService.updateUserFnameById(id, fname);
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String responseBody = "Failed to update unknown user with ID: " + Long.toString(id);
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }
        
        return response;
    }


    /**
     * Change persisted user's last name
     * @param id
     * @param lname
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateUserLnameById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserLnameById(
        @RequestParam long id,
        @RequestParam String lname
    ) throws SQLException {

        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByTenantId(id);

        if (ou.isPresent()) {
            User u = uService.updateUserLnameById(id, lname);
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String responseBody = "Failed to update unknown user with ID: " + Long.toString(id);
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * Change persisted user's username
     * @param id
     * @param newUname
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateUserUnameById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserUnameById(
        @RequestParam long id,
        @RequestParam String newUname
    ) throws SQLException {

        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByTenantId(id);
        
        if (ou.isPresent()) {
            User u = uService.updateUserUnameById(id, newUname);
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String responseBody = "Failed to update unknown user with ID: " + Long.toString(id);
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * Change persisted user's password
     * @param id
     * @param newPswd
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateUserPswdById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserPswdById(
        @RequestParam long id,
        @RequestParam String newPswd
    ) throws SQLException {

        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByTenantId(id);

        if (ou.isPresent()) {
            User u = uService.updateUserPasswordById(id, newPswd);
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String responseBody = "Failed to update unknown user with ID: " + Long.toString(id);
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * Change persisted user's email
     * @param id
     * @param newEmail
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateUserEmailById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserEmailById(
        @RequestParam long id, 
        @RequestParam String newEmail
    ) throws SQLException {
        
        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByTenantId(id);

        if (ou.isPresent()) {
            User u = uService.updateUserEmailById(id, newEmail);
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String responseBody = "Failed to update unknown user with ID: " + Long.toString(id);
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Change persisted user's phone number
     * @param id
     * @param newPnumber
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateUserPnumberById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserPnumberById(
        @RequestParam long id,
        @RequestParam String newPnumber
    ) throws SQLException {

        ResponseEntity<?> response;
        Optional<User> ou = uService.fetchUserByTenantId(id);

        if (ou.isPresent()) {
            User u = uService.updateUserPnumberById(id, newPnumber);
            response = new ResponseEntity<User>(u, HttpStatus.OK);
        }
        else {
            String responseBody = "Failed to update unknown user with ID: " + Long.toString(id);
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
        produces = MediaType.APPLICATION_JSON_VALUE
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
