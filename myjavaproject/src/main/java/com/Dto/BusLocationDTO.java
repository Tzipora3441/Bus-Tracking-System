package com.Dto;

/**
 * DTO למידע על מיקום אוטובוס בציר הנסיעה
 * משמש להצגת מיקום של אוטובוסים פעילים
 */
public class BusLocationDTO {
    
    private Long rideId;              // קוד הנסיעה
    private String lineNumber;        // מספר הקו
    private String busLicensePlate;   // לוחית רישוי
    private String driverName;        // שם הנהג
    private int currentStationOrder;  // מיקום התחנה הנוכחית (לפי מספור)
    private String origin;            // מוצא
    private String destination;       // יעד

    // קונסטרקטור ריק
    public BusLocationDTO() {
    }

    // קונסטרקטור מלא
    public BusLocationDTO(Long rideId, String lineNumber, String busLicensePlate, 
                         String driverName, int currentStationOrder, 
                         String origin, String destination) {
        this.rideId = rideId;
        this.lineNumber = lineNumber;
        this.busLicensePlate = busLicensePlate;
        this.driverName = driverName;
        this.currentStationOrder = currentStationOrder;
        this.origin = origin;
        this.destination = destination;
    }

    // Getters and Setters
    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getBusLicensePlate() {
        return busLicensePlate;
    }

    public void setBusLicensePlate(String busLicensePlate) {
        this.busLicensePlate = busLicensePlate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getCurrentStationOrder() {
        return currentStationOrder;
    }

    public void setCurrentStationOrder(int currentStationOrder) {
        this.currentStationOrder = currentStationOrder;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}