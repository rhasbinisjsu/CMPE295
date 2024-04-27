package com.cropsense.appmanager.Controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.appmanager.Entities.Address;
import com.cropsense.appmanager.Entities.ServiceLocation;
import com.cropsense.appmanager.Entities.User;
import com.cropsense.appmanager.Services.ServiceLocationService;
import com.cropsense.appmanager.Services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/appManager/serviceLocations")
public class ServiceLocationController {
    
    @Autowired
    private ServiceLocationService slService;
    @Autowired
    private UserService uService;

    @PostMapping(
        path = "/createSvcLocation",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createSvcLocation(
        @RequestParam long ownerId,
        @RequestParam String locName,
        @RequestParam String al1,
        @RequestParam String aCity,
        @RequestParam String aState,
        @RequestParam String aCountry,
        @RequestParam String zip
    ) throws SQLException {
        
        ResponseEntity<?> response;

        // check for name used by this user
        Optional<ServiceLocation> oSl = slService.fetchSvcLocationByOwnerIdAndLocName(ownerId, locName);
        if (oSl.isPresent()) {
            String responseBody = "Location name is already allocated";
            response = new ResponseEntity<String>(responseBody, HttpStatus.BAD_REQUEST);
        }
        else {
            // fetch the owner
            User u = uService.fetchUserByTenantId(ownerId).get();

            // create the address
            Address a = new Address();
            a.setLine1(al1);
            a.setCity(aCity);
            a.setCountry(aCountry);
            a.setState(aState);
            a.setZip(zip);

            // create and persist svc loc
            ServiceLocation svcLoc = slService.createServiceLocation(u, locName, a);
            
            response = new ResponseEntity<ServiceLocation>( svcLoc,HttpStatus.OK);
        }

        return response;
    }


    @GetMapping(
        path = "/fetchAllSvcLocations",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAllSvcLocations() throws SQLException {
        
        List<ServiceLocation> slList = slService.fetchAllServiceLocations();

        return new ResponseEntity<List<ServiceLocation>>(slList, HttpStatus.OK);
    }
    
    
    @GetMapping(
        path = "/fetchAllSvcLocationsForOwner",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAllSvcLocationsForOwner(@RequestParam long ownerId)throws SQLException {
        
        List<ServiceLocation> slList = slService.fetchAllServiceLocationsForUser(ownerId);

        return new ResponseEntity<List<ServiceLocation>>(slList, HttpStatus.OK);

    }

    @GetMapping(
        path = "/fetchSvcLocationByLocId",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchSvcLocationByLocId(@RequestParam long locId) throws SQLException {
        ServiceLocation sl = slService.fetchSvcLocationByLocId(locId).get();
        return new ResponseEntity<ServiceLocation>(sl, HttpStatus.OK);
    }

    @DeleteMapping(
        path = "/deleteSvcLocationByLocId"
    )
    public ResponseEntity<?> deleteSvcLocationByLocId(@RequestParam long locId) throws SQLException {
        slService.deleteSvcLocationByLocId(locId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
