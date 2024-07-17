package com.cropsense.metrics_server.EntityEndpoints;

public class SoilMetricEndpoints {

    private final String baseUrl = "/CropSense/AppServer/SoilMetricController";
    private final String getSoilMetricForCrop = "/fetchSoilMetricsForCrop";


    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSoilMetricsForCrop() {
        return getSoilMetricForCrop;
    }
    
}
