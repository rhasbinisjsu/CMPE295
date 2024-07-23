package com.cropsense.metrics_server.EntityEndpoints;

public class FarmEndpoints {
    
    private final String baseUrl = "/CropSense/AppServer/FarmController";
    private final String fetchFarmsForOwner = "/fetchFarmsForOwner";
    
    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getFetchFarmsForOwnerUrl() {
        return this.fetchFarmsForOwner;
    }

}
