package com.cardio_generator.generators;

import java.util.ArrayList;
import java.util.List;

public class AlertManager {
    private List<Alert> alerts;

    public AlertManager() {
        this.alerts = new ArrayList<>();
    }

    public void addAlert(Alert alert) {
        this.alerts.add(alert);
    }

    public List<Alert> getAlerts() {
        return this.alerts;
    }

    public void sendAlerts() {
        // implement the code to send alerts to the appropriate medical staff, 
        //maybeuse a parameter like the staffid to send the alert to the right person
    }
}