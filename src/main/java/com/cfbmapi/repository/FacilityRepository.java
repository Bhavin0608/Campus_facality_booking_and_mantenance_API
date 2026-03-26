package com.cfbmapi.repository;

import com.cfbmapi.entity.Facility;
import com.cfbmapi.entity.FacilityStatus;
import com.cfbmapi.entity.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FacilityRepository extends JpaRepository<Facility,Integer> {

    List<Facility> findAllByType(FacilityType type);
    List<Facility> findAllByStatus(FacilityStatus status);
    List<Facility> findByLocation(String location);
    boolean existsByNameAndLocation(String name, String location);
}
