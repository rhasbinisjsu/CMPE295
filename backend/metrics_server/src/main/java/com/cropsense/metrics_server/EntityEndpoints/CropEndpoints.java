package com.cropsense.metrics_server.EntityEndpoints;

public class CropEndpoints {
    
    private final String baseUrl = "/CropSense/AppServer/CropController";
    private final String fetchAllCropsForFarm =  "/fetchAllCropsForFarm";
    private final String fetchActiveCropsForFarm = "/fetchActiveCropsForFarm";
    private final String fetchCropForFarmBySpecies = "/fetchCropForFarmBySpecies";

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getAllCropsForFarm() {
        return this.fetchAllCropsForFarm;
    }

    public String getActiveCropsForFarm() {
        return this.fetchActiveCropsForFarm;
    }

    public String getCropBySpeciesForFarm() {
        return this.fetchCropForFarmBySpecies;
    }

}
