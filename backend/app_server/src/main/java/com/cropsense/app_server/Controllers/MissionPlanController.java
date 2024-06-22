package com.cropsense.app_server.Controllers;

import java.util.List;
import java.util.Optional;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

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

import com.cropsense.app_server.Entities.MissionPlan;
import com.cropsense.app_server.Logger.AppLogger;
import com.cropsense.app_server.Services.MissionPlanService;

@RestController
@RequestMapping("/CropSense/AppServer/MissionPlanController")
public class MissionPlanController {
    
    @Autowired
    private MissionPlanService mpService;

    // define and create logger object instance
    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Persist a new mission plan
     * @param newMp
     * @return
     * @throws SQLException
     */
    @PostMapping(
        path = "/createMissionPlan",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> createMissionPlan(
        @RequestBody MissionPlan newMp
    ) throws SQLException {

        logger.logInfoMsg("POST [ ENTER ] - Received request to create a new mission plan from user: " + Long.toString(newMp.getOwnerId()));
        mpService.createMissionPlan(newMp);
        logger.logInfoMsg("POST [ EXIT ] - Exiting request to create a new mission plan");
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    /**
     * Fetch all persisted mission plans for an owner
     * @param ownerId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansForOwner",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansForOwner(
        @RequestParam long ownerId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all mission plans for owner with ID: " + Long.toString(ownerId));
        List<MissionPlan> mpList = mpService.fetchMissionPlansForOwner(ownerId);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all mission plans");
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch all persisted mission plans for farm
     * @param ownerId
     * @param farmId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansByFarm",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansByFarm(
        @RequestParam long ownerId,
        @RequestParam long farmId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all mission plans for farm with ID: " + Long.toString(farmId));
        List<MissionPlan> mpList = mpService.fetchMissionPlansByFarm(ownerId, farmId);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all mission plans for farm");
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch all persisted mission plans for owner by status
     * @param ownerId
     * @param status
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansByStatus",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansByStatus(
        @RequestParam long ownerId,
        @RequestParam String status
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all mission plans with status: " + status + " for user with ID: " + Long.toString(ownerId));
        List<MissionPlan> mpList = mpService.fetchMissionPlansByStatus(ownerId, status);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all mission plans by with status: " + status + " for user with ID: " + Long.toString(ownerId));
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch all mission plans for farm by status
     * @param farmId
     * @param status
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansByFarmAndStatus",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansByFarmAndStatus(
        @RequestParam long farmId,
        @RequestParam String status
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all mission plans for farm: " + Long.toString(farmId) + " with status: " + status);
        List<MissionPlan> mpList = mpService.fetchMissionPlansByFarmAndStatus(farmId, status);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all mission plans for farm: " + Long.toString(farmId) + " with status: " + status);
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch all mission plans for user by type
     * @param ownerId
     * @param missionType
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansByMissionType",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansByMissionType(
        @RequestParam long ownerId,
        @RequestParam String missionType
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all mission plans with type: " + missionType + " for user: " + Long.toString(ownerId));
        List<MissionPlan> mpList = mpService.fetchMissionPlansByMissionType(ownerId, missionType);
        logger.logInfoMsg("GET [ EXIT ] - EXITING request to fetch all mission plans with type: " + missionType + " for user: " + Long.toString(ownerId));
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch all persisted mission plans for farm by type
     * @param farmId
     * @param missionType
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansByFarmAndMissionType",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansByFarmAndMissionType(
        @RequestParam long farmId,
        @RequestParam String missionType
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all mission plans with type: " + missionType + " for farm: " + Long.toString(farmId));
        List<MissionPlan> mpList = mpService.fetchMissionPlansByFarmAndMissionType(farmId, missionType);
        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all mission plans with type: " + missionType + " for farm: " + Long.toString(farmId));
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch persisted mission plans for user on a specific date
     * @param ownerId
     * @param date
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansByOwnerAndDate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansByOwnerAndDate(
        @RequestParam long ownerId,
        @RequestParam String date
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch mission plans for user: " + Long.toString(ownerId) + " with date: " + date);

        // convert date string to java.sql.Date object
        Date sqlDate = Date.valueOf(date);
        List<MissionPlan> mpList = mpService.fetchMissionPlansByOwnerIdAndDate(ownerId, sqlDate);

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch mission plans for user: " + Long.toString(ownerId) + " with date: " + date);
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch persisted mission plans for farm on a date 
     * @param farmId
     * @param date
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansByFarmAndDate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansByFarmAndDate(
        @RequestParam long farmId,
        @RequestParam String date
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch mission plans for farm: " + Long.toString(farmId) + " with date: " + date);

        // convert date string to java.sql.Date object
        Date sqlDate = Date.valueOf(date);
        List<MissionPlan> mpList = mpService.fetchMissionPlansForFarmAndDate(farmId, sqlDate);

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch mission plans for farm: " + Long.toString(farmId) + " with date: " + date);
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch persisted mission plans for an owner within a date range
     * @param ownerId
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansForOwnerBetweenDates",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansForOwnerBetweenDates(
        @RequestParam long ownerId,
        @RequestParam String startDate,
        @RequestParam String endDate
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch all mission plans for owner with ID: " + Long.toString(ownerId) + " for date range: " + startDate + " - " + endDate);

        // convert date strings to java.sql.Date
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);
        List<MissionPlan> mpList = mpService.fetchMissionPlansForOwnerBetweenDates(ownerId, sqlStartDate, sqlEndDate);

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch all mission plans for owner with ID: " + Long.toString(ownerId) + " for date range: " + startDate + " - " + endDate);
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);

    }


    /**
     * Fetch persisted mission plans for a farm within a date range
     * @param farmId
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlansForFarmBetweenDates",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MissionPlan>> fetchMissionPlansForFarmBetweenDates(
        @RequestParam Long farmId,
        @RequestParam String startDate,
        @RequestParam String endDate
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Received request to fetch mission plans for Farm with ID: " + Long.toString(farmId) + " for date range: " + startDate + " - " + endDate);

        // convert date strings to java.sql.Date objects
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);
        List<MissionPlan> mpList = mpService.fetchMissionPlansForFarmBetweenDates(farmId, sqlStartDate, sqlEndDate);

        logger.logInfoMsg("GET - [ EXIT ] - Exiting request to fetch mission plans for Farm with ID: " + Long.toString(farmId) + " for date range: " + startDate + " - " + endDate);
        return new ResponseEntity<List<MissionPlan>>(mpList, HttpStatus.OK);
        
    }


    /**
     * Fetch a persisted mission plan by its ID
     * @param mpId
     * @return
     * @throws SQLException
     */
    @GetMapping(
        path = "/fetchMissionPlanById",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchMissionPlanById(
        @RequestParam long mpId
    ) throws SQLException {

        logger.logInfoMsg("GET [ ENTER ] - Receiced request to fetch a mission plan with ID: " + Long.toString(mpId));

        // declare response entity
        ResponseEntity<?> response;

        Optional<MissionPlan> omp = mpService.fetchMissionPlanById(mpId);
        if (omp.isPresent()) {
            logger.logInfoMsg("Mission plan is present and is returned in response");
            response = new ResponseEntity<MissionPlan>(omp.get(), HttpStatus.OK);
        }
        else {
            logger.logErrorMsg("Request mission plan ID: " + Long.toString(mpId) + " does not exit... could not fetch!");
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.logInfoMsg("GET [ EXIT ] - Exiting request to fetch mission plan");
        return response;
    }


    /**
     * Updated a persisted mission plan's description
     * @param mpId
     * @param newDescription
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateMissionDescription",
        consumes = MediaType.TEXT_PLAIN_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateMissionDescription(
        @RequestParam long mpId,
        @RequestBody String newDescription
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update description for mission plan with ID: " + Long.toString(mpId));

        // declare response entity
        ResponseEntity<HttpStatus> response;

        boolean updated = mpService.updateMissionDescription(mpId, newDescription);
        if (updated == true) {
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update description for mission plan with ID: " + Long.toString(mpId));
        return response;

    }


    /**
     * Update a persisted mission's date
     * @param mpId
     * @param newDate
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateMissionDate",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateMissionDate(
        @RequestParam long mpId,
        @RequestParam String newDate
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update date for mission with ID: " + Long.toString(mpId));
        
        // declare response entity
        ResponseEntity<HttpStatus> response; 

        // convert date string into java.sql.Date
        Date sqlDate = Date.valueOf(newDate);

        boolean updated = mpService.updateMissionDate(mpId, sqlDate);
        if (updated == true) {
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update date for mission with ID: " + Long.toString(mpId));
        return response;

    }


    /**
     * Update a persisted mission's time
     * @param mpId
     * @param newTime
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateMissionTime",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateMissionTime(
        @RequestParam long mpId,
        @RequestParam String newTime
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update time for mission with ID: " + Long.toString(mpId));

        // declare response entity
        ResponseEntity<HttpStatus> response;

        // convert time string to java.sql.Time
        Time sqlTime = Time.valueOf(newTime);

        boolean updated = mpService.updateMissionTime(mpId, sqlTime);
        if (updated == true) {
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update time for mission with ID: " + Long.toString(mpId));
        return response;

    }


    /**
     * Update a persisted mission's status
     * @param mpId
     * @param newStatus
     * @return
     * @throws SQLException
     */
    @PutMapping(
        path = "/updateMissionStatus",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpStatus> updateMissionStatus(
        @RequestParam long mpId, 
        @RequestParam String newStatus
    ) throws SQLException {

        logger.logInfoMsg("PUT [ ENTER ] - Received request to update status for mission with ID: " + Long.toString(mpId));
        mpService.updateMissionStatus(mpId, newStatus);
        logger.logInfoMsg("PUT [ EXIT ] - Exiting request to update status for mission with ID: " + Long.toString(mpId));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Delete a persisted mission plan
     * @param mpId
     * @return
     * @throws SQLException
     */
    @DeleteMapping(
        path = "/deleteMissionPlan"
    )
    public ResponseEntity<HttpStatus> deleteMissionPlan(
        @RequestParam long mpId
    ) throws SQLException {

        logger.logInfoMsg("DELETE [ ENTER ] - Received request to delete mission with ID: " + Long.toString(mpId));
        mpService.deleteMissionPlan(mpId);
        logger.logInfoMsg("DELETE [ EXIT ] - Exiting request to delete mission with ID: " + Long.toString(mpId));
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
