package com.cropsense.metrics_server.Controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.metrics_server.Logger.AppLogger;
import com.cropsense.metrics_server.Services.FarmMetricsService;

@RestController
@RequestMapping("/CropSense/MetricsServer/FarmMetricsController")
public class FarmMetricsController {
    
    @Autowired 
    private FarmMetricsService fmService;

    // Declare and instantiate logger
    private final AppLogger logger = new AppLogger(getClass().toString());


    /**
     * Get count of farms owned by farmer
     * @param ownerId
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping(
        path = "/getOwnerFarmCount",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getOwnerFarmCount(
        @RequestParam long ownerId
    ) throws IOException, URISyntaxException, InterruptedException {

        logger.logInfoMsg("[ ENTER ] Received request to count serviced farms for owner with ID: " + Long.toString(ownerId));

        ResponseEntity<?> response;

        int farmCount;
        try {
            farmCount = fmService.getOwnerFarmCount(ownerId);
            response = new ResponseEntity<String>(String.valueOf(farmCount), HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println("Caught Exception... " + e.getCause() + "\nReason: " + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw e; 
        }

        logger.logInfoMsg("[ EXIT ] Exiting request to count farms for owner with ID: " + Long.toString(ownerId));
        return response;

    }

}
