package com.cardio_generator.generators;

public class Alert {
    private int patientId;
    private long timestamp;
    private String alertType;
    private String alertValue;

    public Alert(int patientId, long timestamp, String alertType, String alertValue) {
        this.patientId = patientId;
        this.timestamp = timestamp;
        this.alertType = alertType;
        this.alertValue = alertValue;
    }

    public int getPatientId() {
        return patientId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getAlertValue() {
        return alertValue;
    }
}
