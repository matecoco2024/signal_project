package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

import java.io.IOException;

public class AlertGenerationTest {
    DataStorage storage = new DataStorage();
    @Test
    void testIncreasingTrendInBloodPressure() {
        Patient patient = new Patient(1);
        patient.addRecord(70.0, "DiastolicPressure", 1714376789050L);
        patient.addRecord(95.0, "DiastolicPressure", 1714376789051L);
        patient.addRecord(110.0, "DiastolicPressure", 1714376789052L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().size() == 1);
    }

    @Test
    void testDecreasingTrendInBloodPressure() {
        Patient patient = new Patient(1);
        patient.addRecord(150.0, "SystolicPressure", 1714376789050L);
        patient.addRecord(130.0, "SystolicPressure", 1714376789051L);
        patient.addRecord(100.0, "SystolicPressure", 1714376789052L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        System.out.println();
        alertGenerator.evaluateData(patient);
        assertEquals(1, alertGenerator.getAlerts().size());
    }

    @Test
    void testCriticalThresholds() {
        Patient patient = new Patient(1);
        patient.addRecord(190.0, "SystolicPressure", 1714376789050L);
        patient.addRecord(50.0, "DiastolicPressure", 1714376789051L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().size() == 2);
    }

    @Test
    void testLowSaturation() {
        Patient patient = new Patient(1);
        patient.addRecord(90.0, "Saturation", 1714376789050L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().size() == 1);
    }

    @Test
    void testRapidDropInSaturation() {
        Patient patient = new Patient(1);
        long currentTimestamp = 1714376789050L;
        long nextTimestamp = currentTimestamp + 500000;
        patient.addRecord(100.0, "Saturation", currentTimestamp);
        patient.addRecord(94.0, "Saturation", nextTimestamp);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().size() == 1);
    }

    @Test
    void testLowBloodPressureAndSaturation() {
        Patient patient = new Patient(1);
        patient.addRecord(85.0, "SystolicPressure", 1714376789050L);
        patient.addRecord(90.0, "Saturation", 1714376789051L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().size() == 3);
        // THREE ALERTS
        // WARNING: ALERT: Critical Threshold Pressure
        // WARNING: ALERT:  Low Saturation
        // WARNING: ALERT: Hypotensive Hypoxemia
    }

    @Test
    void testAbnormalHeartRate() {
        Patient patient = new Patient(1);
        patient.addRecord(45.0, "ECG", 1714376789050L);
        patient.addRecord(105.0, "ECG", 1714376789051L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().size() == 2);
    }

    // Assuming you have a method to calculate the time interval between beats
    @Test
    void testIrregularBeatPatterns() {
        Patient patient = new Patient(1);
        patient.addRecord(95, "ECG", 1714376789050L);
        patient.addRecord(69, "ECG", 1714376789051L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(patient);
        assertTrue(alertGenerator.getAlerts().size() == 1);
    }
}