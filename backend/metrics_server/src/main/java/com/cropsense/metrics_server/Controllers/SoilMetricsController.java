package com.cropsense.metrics_server.Controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropsense.metrics_server.Logger.AppLogger;
import com.cropsense.metrics_server.Services.SoilMetricsService;

@RestController
@RequestMapping("/CropSense/MetricsServer/SoilMetricsController")
public class SoilMetricsController {

    @Autowired
    private SoilMetricsService smService;

    private final AppLogger logger = new AppLogger(getClass().toString());

    @GetMapping(
        path = "/getIndividualizedMetricsForCrop",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getIndividualizedMetricsForCrop(
        @RequestParam long cropId
    ) throws URISyntaxException, IOException, InterruptedException {
        logger.logInfoMsg("[ ENTER ] Receieved request");
        HashMap<String,List<String>> responseMap = smService.getIndividualSoilMetricsForCrop(cropId);
        return new ResponseEntity<HashMap<String,List<String>>>(responseMap, HttpStatus.OK);
    }
    
}
