package com.cfbmapi.service;

import com.cfbmapi.dto.FacilityCreateRequest;
import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.FacilityStatus;
import com.cfbmapi.entity.FacilityType;
import com.cfbmapi.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;

    //------------------------- Used to Create the Facility ----------------------------
    @Transactional
    public Facility createFacility(FacilityCreateRequest request){
        try{
            if(facilityRepository.existsByNameAndLocation(request.getName(), request.getLocation()))
                throw new RuntimeException("Duplicate entry, same entry is present..");

            Facility facility = new Facility();

            facility.setName(request.getName());
            facility.setType(request.getType());
            facility.setCapacity(request.getCapacity());
            facility.setLocation(request.getLocation());

            return facilityRepository.save(facility);
        }catch (Exception e){
            throw new RuntimeException("Error in " +e.getMessage());
        }
    }

    //--------------------------- Used for Update the Facility ---------------------------
    @Transactional
    public void updateFacility(int id, FacilityCreateRequest updatedFacility){
        try{
            Facility facility = facilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Facility with id "+id+" doesn't exists"));

            if(updatedFacility.getName() != null)
                facility.setName(updatedFacility.getName());

            if(updatedFacility.getType() != null)
                facility.setType(updatedFacility.getType());

            if(updatedFacility.getCapacity() != 0)
                facility.setCapacity(updatedFacility.getCapacity());

            if(updatedFacility.getLocation() != null)
                facility.setLocation(updatedFacility.getLocation());

            facilityRepository.save(facility);
            System.out.println("Facility is updated successfully");
        }catch (Exception e){
            throw new RuntimeException("Error in updating Facility" +e.getMessage());
        }
    }

    @Transactional
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

    //---------------------- Used for Delete the Facility --------------------------
    @Transactional
    public void deleteFacility(int id){
        try{
            facilityRepository.deleteById(id);
            System.out.println("Facility is Successfully deleted...");
        }catch (Exception e){
            throw new RuntimeException("Error in deleting facility" +e.getMessage());
        }
    }

    //------------------------ Used for Filter the Facility -----------------------
    public Facility getFacilityById(int id){
        return facilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Facility not found"));
    }

    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();
    }

    public List<Facility> getFacilitiesByType(FacilityType type){
        return facilityRepository.findAllByType(type);
    }

    public List<Facility> getAvailableFacilities(){
        return facilityRepository.findAllByStatus(FacilityStatus.AVAILABLE);
    }

}
