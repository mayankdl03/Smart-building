package com.honeywell.smartbuilding.controller;

import com.honeywell.smartbuilding.dto.SensorDTO;
import com.honeywell.smartbuilding.dto.SensorReadingDTO;
import com.honeywell.smartbuilding.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@Tag(name = "Advanced Sensor Management", description = "Enterprise APIs for Smart Building Sensors with Telemetry")
public class SensorController {

    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    @Operation(summary = "Get all sensors (Paginated)", description = "Retrieves a paginated list of all sensors.")
    public ResponseEntity<Page<SensorDTO>> getAllSensors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching sensors page {} with size {}", page, size);
        return new ResponseEntity<>(sensorService.getAllSensors(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search sensors", description = "Filter sensors by type and active status with pagination.")
    public ResponseEntity<Page<SensorDTO>> searchSensors(
            @RequestParam String type,
            @RequestParam Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Searching sensors: type={}, isActive={}", type, isActive);
        return new ResponseEntity<>(sensorService.searchSensors(type, isActive, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a sensor by ID")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable Long id) {
        logger.info("Fetching sensor with ID {}", id);
        return new ResponseEntity<>(sensorService.getSensorById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add a new sensor")
    public ResponseEntity<SensorDTO> addSensor(@Valid @RequestBody SensorDTO sensorDTO) {
        logger.info("Adding new sensor: {}", sensorDTO.getName());
        return new ResponseEntity<>(sensorService.addSensor(sensorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a sensor")
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable Long id, @Valid @RequestBody SensorDTO sensorDTO) {
        logger.info("Updating sensor with ID {}", id);
        return new ResponseEntity<>(sensorService.updateSensor(id, sensorDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sensor")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        logger.info("Deleting sensor with ID {}", id);
        sensorService.deleteSensor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --- Advanced Telemetry Endpoints ---

    @PostMapping("/{id}/readings")
    @Operation(summary = "Record a new sensor reading", description = "Simulates a sensor sending telemetry data.")
    public ResponseEntity<SensorReadingDTO> addReading(
            @PathVariable Long id,
            @RequestParam Double value) {
        logger.info("Recording new reading {} for sensor ID {}", value, id);
        return new ResponseEntity<>(sensorService.addReading(id, value), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/readings")
    @Operation(summary = "Get all readings for a sensor", description = "Retrieves the historical telemetry data for a specific sensor.")
    public ResponseEntity<List<SensorReadingDTO>> getReadings(@PathVariable Long id) {
        return new ResponseEntity<>(sensorService.getReadings(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/average-reading")
    @Operation(summary = "Get average reading (Analytics)", description = "Calculates the average value of all readings for a sensor using a custom JPA query.")
    public ResponseEntity<Double> getAverageReading(@PathVariable Long id) {
        logger.info("Calculating average reading for sensor ID {}", id);
        return new ResponseEntity<>(sensorService.getAverageReading(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/sync")
    @Operation(summary = "Sync sensor to Cloud (Fault Tolerant)", description = "Demonstrates Spring Retry. It randomly fails and automatically retries 3 times before using a fallback method.")
    public ResponseEntity<String> syncSensorToCloud(@PathVariable Long id) {
        logger.info("Sync endpoint hit for sensor ID {}", id);
        return new ResponseEntity<>(sensorService.syncWithCloud(id), HttpStatus.OK);
    }
}
