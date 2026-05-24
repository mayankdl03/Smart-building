package com.honeywell.smartbuilding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import com.honeywell.smartbuilding.validation.ValidSensorType;

public class SensorDTO {

    private Long id;

    @NotBlank(message = "Sensor name cannot be empty")
    private String name;

    @NotBlank(message = "Sensor type cannot be empty")
    @ValidSensorType
    private String type;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotNull(message = "Status cannot be null")
    private Boolean isActive;

    private LocalDateTime lastMaintained;
    
    private int totalReadings;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }

    public LocalDateTime getLastMaintained() { return lastMaintained; }
    public void setLastMaintained(LocalDateTime lastMaintained) { this.lastMaintained = lastMaintained; }

    public int getTotalReadings() { return totalReadings; }
    public void setTotalReadings(int totalReadings) { this.totalReadings = totalReadings; }
}
