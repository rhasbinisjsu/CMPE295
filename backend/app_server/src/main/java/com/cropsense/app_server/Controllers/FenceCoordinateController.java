package com.cropsense.app_server.Controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.app_server.Entities.FenceCoordinate;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.FenceCoordinateService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/CropSense/AppServer/FenceCoordinateController")
public class FenceCoordinateController {
    
    @Autowired
    private FenceCoordinateService fcService;

    // define and create logger object
    AppLogger logger = new AppLogger(getClass().toString());

    @PostMapping(
        path = "/createFenceCoordinates",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Transactional
    public ResponseEntity<HttpStatus> createFenceCoordinates(
        @RequestBody List<FenceCoordinate> coordinates
    ) throws SQLException {
        
        logger.logInfoMsg("POST [ ENTER ] - Received request to persist fence coordinates");

        logger.logInfoMsg("Checking if fence has coordinates");
        if (fcService.fetchCoordinatesForFence(coordinates.get(0).getFenceId()).size() > 0) {
            logger.logInfoMsg("Found fence coordinates for fence with ID: " + Long.toString(coordinates.get(0).getFenceId()));
            fcService.deleteCoordinatesForFence(coordinates.get(0).getFenceId());
            logger.logInfoMsg("Deleted old fence coordinates");
        }
        else {
            logger.logInfoMsg("No coordinates found for fence with ID: " + Long.toString(coordinates.get(0).getFenceId()));
        }

        for (int i = 0; i < coordinates.size(); i++) {
            fcService.createFenceCoordinate(coordinates.get(i));
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to persist a fence coordinates");
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    /**
     * Fetch persisted coordinates for a farm fence
     * @param fenceId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchFenceCoordinates",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<FenceCoordinate>> fetchFenceCoordinates(
        @RequestParam long fenceId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch fence coordinates for fence with ID: " + Long.toString(fenceId));
        List<FenceCoordinate> fcList = fcService.fetchCoordinatesForFence(fenceId);
        
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch fence coordinates");
        return new ResponseEntity<List<FenceCoordinate>>(fcList, HttpStatus.OK);
    }


    /**
     * Delete persisted fence cooridnates for fence
     * @param fenceId
     * @return
     * @throws SQLException
     */
    @DeleteMapping(
        path = "/deleteCoordinatesForFence"
    )
    public ResponseEntity<HttpStatus> deleteCoordinatesForFence(@RequestParam long fenceId) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete farm fence coordinates for fence with ID: " + Long.toString(fenceId));
        fcService.deleteCoordinatesForFence(fenceId);
        
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete farm fence coordinates");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
