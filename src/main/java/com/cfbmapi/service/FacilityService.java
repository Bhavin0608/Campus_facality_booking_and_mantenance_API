package com.cfbmapi.service;

import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.FacilityStatus;
import com.cfbmapi.entity.FacilityType;
import com.cfbmapi.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;

    public Facility createFacility(String name, FacilityType type, int capacity, String location){
        try{
            Facility facility = new Facility();

            facility.setName(name);
            facility.setType(type);
            facility.setCapacity(capacity);
            facility.setLocation(location);

            return facilityRepository.save(facility);
        }catch (Exception e){
            throw new RuntimeException("Error in " +e.getMessage());
        }
    }

    public Facility getFacilityById(int id){
        return facilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Facility not found"));
    }

    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();
    }

    public void updateFacility(int id, Facility updatedFacility){
        try{
            Facility facility = facilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Facility with id "+id+" doesn't exists"));

            facility.setName(updatedFacility.getName());
            facility.setType(updatedFacility.getType());
            facility.setCapacity(updatedFacility.getCapacity());
            facility.setLocation(updatedFacility.getLocation());

            facilityRepository.save(facility);
            System.out.println("Facility is updated successfully");
        }catch (Exception e){
            throw new RuntimeException("Error in updating Facility" +e.getMessage());
        }
    }

    public void deleteFacility(int id){
        try{
            Facility facility = facilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Such Facility with id "+id+" not exists."));

            facilityRepository.deleteById(id);
            System.out.println("Facility is Successfully deleted...");
        }catch (Exception e){
            throw new RuntimeException("Error in deleting facility" +e.getMessage());
        }
    }

    public List<Facility> getFacilitiesByType(FacilityType type){
        return facilityRepository.findAllByType(type);
    }

    public List<Facility> getAvailableFacilities(){
        return facilityRepository.findAllByStatus(FacilityStatus.AVAILABLE);
    }

    public void updateFacilityStatus(int id,FacilityStatus status){
        try{
            Facility facility = facilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Such Facility with id "+id+" not exists."));

            if(facility.getStatus() == status)
                throw new RuntimeException("Change the status, It is same as old one");

            facility.setStatus(status);
            facilityRepository.save(facility);

            System.out.println("Status is updated Successfully");
        }catch (Exception e){
            throw new RuntimeException("Error in updating facility" +e.getMessage());
        }
    }

}
