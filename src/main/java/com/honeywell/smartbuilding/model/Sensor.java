package com.honeywell.smartbuilding.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sensor extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean isActive;

    private LocalDateTime lastMaintained;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SensorReading> readings = new ArrayList<>();

    public Sensor() {
    }

    public Sensor(String name, String type, String location, Boolean isActive) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.isActive = isActive;
        this.lastMaintained = LocalDateTime.now();
    }

    public void addReading(SensorReading reading) {
        readings.add(reading);
        reading.setSensor(this);
    }

    public void removeReading(SensorReading reading) {
        readings.remove(reading);
        reading.setSensor(null);
    }

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

    public List<SensorReading> getReadings() { return readings; }
    public void setReadings(List<SensorReading> readings) { this.readings = readings; }
}
