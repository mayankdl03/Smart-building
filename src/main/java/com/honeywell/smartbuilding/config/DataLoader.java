package com.honeywell.smartbuilding.config;

import com.honeywell.smartbuilding.model.Sensor;
import com.honeywell.smartbuilding.model.SensorReading;
import com.honeywell.smartbuilding.repository.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner initDatabase(SensorRepository repository) {
        return args -> {
            logger.info("Loading initial dummy data for testing...");
            
            Sensor s1 = new Sensor("Main Lobby AC Temp", "Temperature", "Floor 1 - Lobby", true);
            Sensor s2 = new Sensor("Cafeteria Smoke Detector", "Smoke", "Floor 2 - Cafeteria", true);
            
            // Add some dummy telemetry data to the AC sensor
            s1.addReading(new SensorReading(22.5, s1));
            s1.addReading(new SensorReading(23.0, s1));
            s1.addReading(new SensorReading(24.1, s1));
            
            // Add dummy data to Smoke detector
            s2.addReading(new SensorReading(0.01, s2)); // Normal
            s2.addReading(new SensorReading(0.02, s2)); // Normal
            
            repository.saveAll(List.of(s1, s2));
            
            logger.info("Dummy data and telemetry loaded successfully! View it at Swagger UI.");
        };
    }
}
