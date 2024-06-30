package com.cropsense.app_server.Controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.app_server.Entities.Crop;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.CropService;

@RestController
@RequestMapping("/CropSense/AppServer/CropController")
public class CropController {
    
    @Autowired
    private CropService cropService;

    // define and instantiate logger object
    private final AppLogger logger = new AppLogger(getClass().toString());

    
    /**
     * Persist a new crop for a farm
     * @param newCrop
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createCrop",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> createCrop(
        @RequestBody Crop newCrop
    ) throws SQLException {

        long farmId = newCrop.getFarmId();

        logger.logInfoMsg("POST [ ENTER ] - Received request to create a new Crop for farm with ID: " + Long.toString(farmId) );

        ResponseEntity<HttpStatus> response;
        boolean created;

        created = cropService.createCrop(newCrop);

        if (created) {
            response = new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.logErrorMsg("Failed to persist a new crop for farm with ID: " + Long.toString(farmId));
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to create a new crop");
        return response;

    }


    /**
     * Fetch all persisted crops for a farm
     * @param farmId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchAllCropsForFarm",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAllCropsForFarm(
        @RequestParam long farmId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all crops for farm with ID: " + Long.toString(farmId));

        ResponseEntity<?> response; 

        try {
            List<Crop> cropList = cropService.fetchCropsForFarm(farmId);
            response = new ResponseEntity<List<Crop>>(cropList, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all crops for farm");
        return response;

    }


    /**
     * Fetch persisted active crops for a farm
     * @param farmId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchActiveCropsForFarm",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchActiveCropsForFarm(
        @RequestParam long farmId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch active crops for farm with ID: " + Long.toString(farmId));

        ResponseEntity<?> response;

        try {
            List<Crop> cropList = cropService.fetchActiveCropsForFarm(farmId);
            response = new ResponseEntity<List<Crop>>(cropList, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch active crops for farm");
        return response;

    }


    /**
     * Fetch crops for a farm by status
     * @param farmId
     * @param status
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchCropsForFarmByStatus",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchCropsForFarmByStatus(
        @RequestParam long farmId,
        @RequestParam String status
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch crops with status: " + status + " for farm with ID: " + Long.toString(farmId));

        ResponseEntity<?> response;

        try {
            List<Crop> cropList = cropService.fetchCropsForFarmByStatus(farmId, status);
            response = new ResponseEntity<List<Crop>>(cropList, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch crops with status: " + status);
        return response;

    }


    /**
     * Fetch persisted crops for a farm by species
     * @param farmId
     * @param species
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchCropForFarmBySpecies",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchCropForFarmBySpecies(
        @RequestParam long farmId,
        @RequestParam String species
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch crops of species: " + species + " for farm with ID: " + Long.toString(farmId));

        ResponseEntity<?> response;

        try {
            List<Crop> cropList = cropService.fetchCropForFarmBySpecies(farmId, species);
            response = new ResponseEntity<List<Crop>>(cropList, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch crops of species");
        return response;

    }


    /**
     * Fetch persisted crops for a farm by year
     * @param farmId
     * @param year
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchCropsForFarmByYear",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchCropsForFarmByYear(
        @RequestParam long farmId,
        @RequestParam String year
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch crops in year: " + year + " for farm with ID: " + Long.toString(farmId));

        ResponseEntity<?> response;

        try {
            List<Crop> cropList = cropService.fetchCropsForFarmByYear(farmId, year);
            response = new ResponseEntity<List<Crop>>(cropList, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch crops in year");
        return response;

    }


    /**
     * Fetch a persisted crop by its ID
     * @param cropId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchCropById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchCropById(
        @RequestParam long cropId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch crop data for ID: " + cropId);

        ResponseEntity<?> response; 

        try {
            Optional<Crop> oc = cropService.fetchCropById(cropId);
            
            if (oc.isPresent()) {
                logger.logInfoMsg("Optional<Crop> object is populated");
                Crop c = oc.get();
                response = new ResponseEntity<Crop>(c, HttpStatus.OK);
            }
            else {
                logger.logWarnMsg("Optional<Crop> object is empty");
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch crop data");
        return response;

    }


    /**
     * Update a persisted crop's transplant amount
     * @param cropId
     * @param transplantAmount
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateCropTransplantAmount",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateCropTransplantAmount(
        @RequestParam long cropId,
        @RequestParam int transplantAmount
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update transplanted amount for crop with ID: " + Long.toString(cropId) + " to: " + String.valueOf(transplantAmount));

        ResponseEntity<HttpStatus> response;

        try {
            boolean updated = false;
            updated = cropService.updateCropTransplantAmount(cropId, transplantAmount);

            if (updated) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to UPDATE");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update transplanted amount for crop with ID: " + Long.toString(cropId));
        return response;

    }


    /**
     * Update a persisted crop's cultivated amount
     * @param cropId
     * @param cultivatedAmount
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateCropCultivatedAmount",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateCropCultivatedAmount(
        @RequestParam long cropId,
        @RequestParam int cultivatedAmount
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update cultivated amount for crop with ID: " + Long.toString(cultivatedAmount) + " to: " + String.valueOf(cultivatedAmount));

        ResponseEntity<HttpStatus> response;

        try {
            boolean updated = false;
            updated = cropService.updateCropCultivatedAmount(cropId, cultivatedAmount);

            if (updated) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to UPDATE");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update cultivated amount for crop with ID: " + Long.toString(cultivatedAmount));
        return response;

    }


    /**
     * Update a persisted crop's isActive flag
     * @param cropId
     * @param isActive
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateCropIsActiveFlag",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateCropIsActiveFlag(
        @RequestParam long cropId,
        @RequestParam boolean isActive
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update isActive flag to: " + String.valueOf(isActive) + " for crop with ID: " + Long.toString(cropId));

        ResponseEntity<HttpStatus> response;

        try {
            boolean updated = false;
            updated = cropService.updateCropIsActiveFlag(cropId, isActive);

            if (updated) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to UPDATE");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update isActive flag for crop with ID: " + Long.toString(cropId));
        return response;

    }


    /**
     * Update a persisted crop's status
     * @param cropId
     * @param status
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateCropStatus",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateCropStatus(
        @RequestParam long cropId,
        @RequestParam String status
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update crop status to: " + status + " for crop with ID: " + Long.toString(cropId));

        ResponseEntity<HttpStatus> response;

        try {
            boolean updated = false;
            updated = cropService.updateCropStatus(cropId, status);

            if (updated) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to UPDATE");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update crop status for crop with ID: " + Long.toString(cropId));
        return response;

    }


    /**
     * Update a persisted crop's start date
     * @param cropId
     * @param startDate
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateCropStartDate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateCropStartDate(
        @RequestParam long cropId,
        @RequestParam String startDate
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update start date to: " + startDate + " for crop with ID: " + Long.toString(cropId));

        ResponseEntity<HttpStatus> response;

        // convert date String to java.sql.Date
        Date sqlStartDate = Date.valueOf(startDate);

        try {
            boolean updated = false;
            updated = cropService.updateStartDate(cropId, sqlStartDate);

            if (updated) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to UPDATE");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update start date for crop with ID: " + Long.toString(cropId));
        return response;

    }


    /**
     * Update a persisted crop's end date
     * @param cropId
     * @param endDate
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateCropEndDate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateCropEndDate(
        @RequestParam long cropId,
        @RequestParam String endDate
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update end date to: " + endDate + " for crop with ID: " + Long.toString(cropId));
        
        ResponseEntity<HttpStatus> response;

        // convert date String to java.sql.Date
        Date sqlEndDate = Date.valueOf(endDate);

        try {
            boolean updated = false;
            updated = cropService.updateEndDate(cropId, sqlEndDate);

            if (updated) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to UPDATE");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to updated end date for crop with ID: " + Long.toString(cropId));
        return response;

    }


    /**
     * Delete a persisted crop by its ID
     * @param cropId
     * @return
     * @throws SQLException
     */
    @DeleteMapping(
        path = "/deleteCropById"
    )
    public ResponseEntity<HttpStatus> deleteCropById(
        @RequestParam long cropId
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete crop with ID: " + Long.toString(cropId));
        ResponseEntity<HttpStatus> response;

        try {
            boolean deleted = false;
            deleted = cropService.deleteCrop(cropId);

            if (deleted) {
                response = new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
                logger.logWarnMsg("Failed to delete crop with ID: " + Long.toString(cropId));
            }

        }
        catch (Exception e) {
            logger.logErrorMsg("Caught exception... Failed to DELETE");
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete crop with ID: " + Long.toString(cropId));
        return response;

    }

}
