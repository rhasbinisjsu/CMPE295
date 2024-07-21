package com.cropsense.metrics_server.EntityEndpoints;

public class SpeciesMetricEndpoints {
    
    private final String baseUrl = "/CropSense/AppServer/SpeciesMetricController";
    private final String fetchEntriesForCropUrl = baseUrl + "/fetchSpeciesMetricEntriesForCrop";

    public String getEntriesForCropUrl() {
        return this.fetchEntriesForCropUrl;
    }

}
