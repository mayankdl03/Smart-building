package com.honeywell.smartbuilding.event;

import org.springframework.context.ApplicationEvent;

public class SensorAnomalyEvent extends ApplicationEvent {

    private final String sensorName;
    private final String location;
    private final String issueDescription;

    public SensorAnomalyEvent(Object source, String sensorName, String location, String issueDescription) {
        super(source);
        this.sensorName = sensorName;
        this.location = location;
        this.issueDescription = issueDescription;
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getLocation() {
        return location;
    }

    public String getIssueDescription() {
        return issueDescription;
    }
}
