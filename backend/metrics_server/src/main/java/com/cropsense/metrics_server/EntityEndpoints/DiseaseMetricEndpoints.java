package com.cropsense.metrics_server.EntityEndpoints;

public class DiseaseMetricEndpoints {
    
    private final String baseUrl = "/CropSense/AppServer/DiseaseMetricController";
    private final String fetchAllDiseaseEntriesForCrop = baseUrl + "/fetchDiseaseEntriesForCrop";

    public String getAllDiseaseEntriesForCrop() {
        return this.fetchAllDiseaseEntriesForCrop;
    }

}
