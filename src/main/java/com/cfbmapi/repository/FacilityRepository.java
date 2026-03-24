package com.cfbmapi.repository;

import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.FacilityStatus;
import com.cfbmapi.entity.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility,Integer> {

    List<Facility> findAllByType(FacilityType type);
    List<Facility> findAllByStatus(FacilityStatus status);
    List<Facility> findByLocation(String location);
    boolean existsByNameAndLocation(String name, String location);
}
