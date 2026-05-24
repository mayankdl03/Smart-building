package com.honeywell.smartbuilding.repository;

import com.honeywell.smartbuilding.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    
    List<SensorReading> findBySensorIdOrderByTimestampDesc(Long sensorId);
    
    @Query("SELECT AVG(r.value) FROM SensorReading r WHERE r.sensor.id = :sensorId")
    Double getAverageReadingBySensorId(@Param("sensorId") Long sensorId);
}
