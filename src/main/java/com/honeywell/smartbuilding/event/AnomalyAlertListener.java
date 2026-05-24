package com.honeywell.smartbuilding.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnomalyAlertListener {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyAlertListener.class);

    @EventListener
    public void handleSensorAnomaly(SensorAnomalyEvent event) {
        // In a real application, this would send an Email, SMS, or PagerDuty alert.
        logger.error("🚨 [EVENT-DRIVEN ALERT] 🚨 Dispatched to Admin! Sensor: '{}', Location: '{}'. Issue: {}",
                event.getSensorName(), event.getLocation(), event.getIssueDescription());
    }
}
