package com.honeywell.smartbuilding.service;

import com.honeywell.smartbuilding.dto.SensorDTO;
import com.honeywell.smartbuilding.dto.SensorReadingDTO;
import com.honeywell.smartbuilding.exception.ResourceNotFoundException;
import com.honeywell.smartbuilding.model.Sensor;
import com.honeywell.smartbuilding.model.SensorReading;
import com.honeywell.smartbuilding.repository.SensorReadingRepository;
import com.honeywell.smartbuilding.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SensorReadingRepository readingRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, SensorReadingRepository readingRepository) {
        this.sensorRepository = sensorRepository;
        this.readingRepository = readingRepository;
    }

    public Page<SensorDTO> getAllSensors(Pageable pageable) {
        return sensorRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Page<SensorDTO> searchSensors(String type, Boolean isActive, Pageable pageable) {
        return sensorRepository.findByTypeAndIsActive(type, isActive, pageable).map(this::convertToDTO);
    }

    @Cacheable(value = "sensors", key = "#id")
    public SensorDTO getSensorById(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id: " + id));
        return convertToDTO(sensor);
    }

    public SensorDTO addSensor(SensorDTO sensorDTO) {
        Sensor sensor = new Sensor(sensorDTO.getName(), sensorDTO.getType(), sensorDTO.getLocation(), sensorDTO.getIsActive());
        Sensor savedSensor = sensorRepository.save(sensor);
        return convertToDTO(savedSensor);
    }

    @CacheEvict(value = "sensors", key = "#id")
    public SensorDTO updateSensor(Long id, SensorDTO sensorDetails) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id: " + id));
        
        sensor.setName(sensorDetails.getName());
        sensor.setType(sensorDetails.getType());
        sensor.setLocation(sensorDetails.getLocation());
        sensor.setIsActive(sensorDetails.getIsActive());
        
        Sensor updatedSensor = sensorRepository.save(sensor);
        return convertToDTO(updatedSensor);
    }

    @CacheEvict(value = "sensors", key = "#id")
    public void deleteSensor(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id: " + id));
        sensorRepository.delete(sensor);
    }

    // Advanced features: Telemetry tracking
    public SensorReadingDTO addReading(Long sensorId, Double value) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id: " + sensorId));
        
        SensorReading reading = new SensorReading(value, sensor);
        SensorReading savedReading = readingRepository.save(reading);
        
        SensorReadingDTO dto = new SensorReadingDTO();
        dto.setId(savedReading.getId());
        dto.setValue(savedReading.getValue());
        dto.setTimestamp(savedReading.getTimestamp());
        dto.setSensorId(sensor.getId());
        return dto;
    }

    public List<SensorReadingDTO> getReadings(Long sensorId) {
        if (!sensorRepository.existsById(sensorId)) {
            throw new ResourceNotFoundException("Sensor not found with id: " + sensorId);
        }
        return readingRepository.findBySensorIdOrderByTimestampDesc(sensorId)
                .stream().map(reading -> {
                    SensorReadingDTO dto = new SensorReadingDTO();
                    dto.setId(reading.getId());
                    dto.setValue(reading.getValue());
                    dto.setTimestamp(reading.getTimestamp());
                    dto.setSensorId(reading.getSensor().getId());
                    return dto;
                }).collect(Collectors.toList());
    }

    public Double getAverageReading(Long sensorId) {
        if (!sensorRepository.existsById(sensorId)) {
            throw new ResourceNotFoundException("Sensor not found with id: " + sensorId);
        }
        Double avg = readingRepository.getAverageReadingBySensorId(sensorId);
        return avg != null ? avg : 0.0;
    }

    // --- Principal Engineer Feature: Fault Tolerance & Retries ---
    
    // Simulating a call to Honeywell Cloud that randomly fails
    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public String syncWithCloud(Long sensorId) {
        System.out.println("🔄 Attempting to sync Sensor " + sensorId + " with Cloud...");
        if (Math.random() > 0.3) { // 70% chance to fail
            System.out.println("❌ Network failure! Throwing exception...");
            throw new RuntimeException("Honeywell Cloud API is down");
        }
        return "✅ Sync Successful for Sensor " + sensorId;
    }

    // This method runs automatically if all 3 retries fail
    @Recover
    public String recoverSyncError(RuntimeException e, Long sensorId) {
        System.out.println("🛡️ Circuit Breaker / Recovery triggered for Sensor " + sensorId);
        return "⚠️ Sync Failed. Fallback mode activated. Error: " + e.getMessage();
    }

    private SensorDTO convertToDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setName(sensor.getName());
        dto.setType(sensor.getType());
        dto.setLocation(sensor.getLocation());
        dto.setIsActive(sensor.getIsActive());
        dto.setLastMaintained(sensor.getLastMaintained());
        dto.setTotalReadings(sensor.getReadings() != null ? sensor.getReadings().size() : 0);
        return dto;
    }
}
