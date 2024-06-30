package com.cropsense.app_server.Controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.app_server.Entities.MissionCoordinate;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.MissionCoordinateService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/CropSense/AppServer/MissionCoordinateController")
public class MissionCoordinateController {
    
    @Autowired
    private MissionCoordinateService mcService;

    // declare & create logger object instance
    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a list of coordinates for a mission
     * @param mcList
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createMissionCoordinates",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Transactional
    public ResponseEntity<HttpStatus> createMissionCoordinates(
        @RequestBody List<MissionCoordinate> mcList
    ) throws SQLException {

        logger.logInfoMsg("POST [ ENTER ] - Received request to persist coordinates for mission with ID: " + Long.toString(mcList.get(0).getMissionId()));

        // coordinate count
        int coordCount = mcList.size();
        logger.logInfoMsg("Received " + String.valueOf(coordCount) + " to persist") ;

        for (int i = 0; i < coordCount; i++) {
            logger.logInfoMsg("Persisting item " + String.valueOf(i + 1) + "/" + String.valueOf(coordCount) + " coordinates");
            MissionCoordinate newMc = mcList.get(i);
            mcService.createMissionCoordinate(newMc);
        }

        logger.logInfoMsg("POST [ EXIT ] - Exiting request to persist coordinates");
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    /**
     * Fetch a persisted mission plan coordinate 
     * @param coordinateId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionCoordinate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchMissionCoordinate(
        @RequestParam long coordinateId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch mission coordinate with ID: " + Long.toString(coordinateId));

        // init response
        ResponseEntity<?> response;

        Optional<MissionCoordinate> omc = mcService.fetchMissionCoordinate(coordinateId);
        
        if (omc.isPresent()) {
            MissionCoordinate mc = omc.get();
            response = new ResponseEntity<MissionCoordinate>(mc, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting  request to fetch mission coordinate with ID: " + Long.toString(coordinateId));
        return response;

    }


    /**
     * Fetch persisted coordinates for a mission
     * @param missionId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchCoordinatesForMission",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionCoordinate>> fetchCoordinatesForMission(
        @RequestParam long missionId        
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch coordinates for mission with ID: " + Long.toString(missionId));
        List<MissionCoordinate> mcList = mcService.fetchCoordinatesForMission(missionId);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch coordinates for mission with ID: " + Long.toString(missionId));
        return new ResponseEntity<List<MissionCoordinate>>(mcList, HttpStatus.OK);

    }


    /**
     * Delete a persisted mission coordinate
     * @param coordId
     * @return
     * @throws SQLException
     */
    @Transactional
    @DeleteMapping(
        path = "/deleteMissionCoordinate"
    )
    public ResponseEntity<HttpStatus> deleteMissionCoordinate(
        @RequestParam long coordId
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete mission coordinate with ID: " + Long.toString(coordId));
        mcService.deleteMissionCoordinate(coordId);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete mission coordinate with ID: " + Long.toString(coordId));
        return new ResponseEntity<>(HttpStatus.OK);

    }


    /**
     * Delete persisted coordinates for a mission
     * @param missionId
     * @return
     * @throws SQLException
     */
    @Transactional
    @DeleteMapping(
        path = "/deleteCoordinatesForMission"
    )
    public ResponseEntity<HttpStatus> deleteCoordinatesForMission(
        @RequestParam long missionId
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received reqiest to delete coordinates for mission with ID: " + Long.toString(missionId));
        mcService.deleteCoordinatesForMission(missionId);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete coordinates for mission with ID: " + Long.toString(missionId));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

