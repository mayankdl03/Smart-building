package com.honeywell.smartbuilding.job;

import com.honeywell.smartbuilding.model.Sensor;
import com.honeywell.smartbuilding.model.SensorReading;
import com.honeywell.smartbuilding.repository.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.honeywell.smartbuilding.event.SensorAnomalyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnomalyDetectionJob {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectionJob.class);
    private final SensorRepository sensorRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AnomalyDetectionJob(SensorRepository sensorRepository, ApplicationEventPublisher eventPublisher) {
        this.sensorRepository = sensorRepository;
        this.eventPublisher = eventPublisher;
    }

    // Runs every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void detectAnomalies() {
        logger.info("Running Anomaly Detection Job...");
        
        List<Sensor> activeSensors = sensorRepository.findByIsActive(true);
        for (Sensor sensor : activeSensors) {
            if ("Temperature".equals(sensor.getType())) {
                List<SensorReading> readings = sensor.getReadings();
                if (!readings.isEmpty()) {
                    SensorReading latestReading = readings.get(readings.size() - 1);
                    if (latestReading.getValue() > 30.0) {
                        String issue = "High temperature: " + latestReading.getValue() + "°C";
                        eventPublisher.publishEvent(new SensorAnomalyEvent(this, sensor.getName(), sensor.getLocation(), issue));
                    }
                }
            } else if ("Smoke".equals(sensor.getType())) {
                List<SensorReading> readings = sensor.getReadings();
                if (!readings.isEmpty()) {
                    SensorReading latestReading = readings.get(readings.size() - 1);
                    if (latestReading.getValue() > 0.05) {
                        String issue = "Smoke detected: " + latestReading.getValue();
                        eventPublisher.publishEvent(new SensorAnomalyEvent(this, sensor.getName(), sensor.getLocation(), issue));
                    }
                }
            }
        }
    }
}
