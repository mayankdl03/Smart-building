package com.honeywell.smartbuilding.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SensorReadingDTO {

    private Long id;
    
    @NotNull(message = "Value cannot be null")
    private Double value;
    
    private LocalDateTime timestamp;
    
    private Long sensorId;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Long getSensorId() { return sensorId; }
    public void setSensorId(Long sensorId) { this.sensorId = sensorId; }
}
