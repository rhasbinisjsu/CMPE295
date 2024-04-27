package com.cropsense.appmanager.Services;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropsense.appmanager.Entities.Address;
import com.cropsense.appmanager.Entities.ServiceLocation;
import com.cropsense.appmanager.Entities.User;
import com.cropsense.appmanager.Repositories.ServiceLocationRepository;

@Service
public class ServiceLocationService {
    
    @Autowired
    private ServiceLocationRepository slRepo;

    public ServiceLocation createServiceLocation(
        User owner, 
        String locName,
        Address address
    ) throws SQLException {
        ServiceLocation sl = new ServiceLocation();
        sl.setOwner(owner);
        sl.setLocationName(locName);
        sl.setAddress(address);
        slRepo.save(sl);

        return sl;
    } 

    
    public List<ServiceLocation> fetchAllServiceLocations() throws SQLException{
        
        List<ServiceLocation> slList = slRepo.findAll();
        return slList;

    }


    public List<ServiceLocation> fetchAllServiceLocationsForUser(long id) throws SQLException {
        
        List<ServiceLocation> slList = slRepo.findByOwnerTenantId(id);
        return slList;

    }

    public Optional<ServiceLocation> fetchSvcLocationByLocId(long locId) throws SQLException {
        
        Optional<ServiceLocation> oSl = slRepo.findById(locId);
        return oSl;

    }

    public Optional<ServiceLocation> fetchSvcLocationByOwnerIdAndLocName(long ownerId, String locName) throws SQLException {
        
        Optional<ServiceLocation> oSl = slRepo.findByOwnerTenantIdAndLocName(ownerId, locName);
        return oSl;

    }

    public void deleteSvcLocationByLocId(long locId) throws SQLException {
        slRepo.deleteById(locId);
    }

}
