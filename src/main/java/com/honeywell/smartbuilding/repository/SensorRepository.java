package com.honeywell.smartbuilding.repository;

import com.honeywell.smartbuilding.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByIsActive(Boolean isActive);
    
    Page<Sensor> findByTypeAndIsActive(String type, Boolean isActive, Pageable pageable);
}
