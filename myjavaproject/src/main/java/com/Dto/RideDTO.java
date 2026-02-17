package com.Dto;

import java.time.LocalTime;

public class RideDTO {
    private Long id;
    private LocalTime departureTime;
    
    // מזהים שהמשתמש שולח
     private String lineNumber;
    private Long busId;
    private Long driverId;
    
    // מידע שחוזר אוטומטית (לקריאה בלבד)
   
    private String origin;
    private String destination;
    private String busLicensePlate; // דוגמה למידע נוסף מהאוטובוס
    private String driverName;       // דוגמה למידע נוסף מהנהג

    public RideDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }

    public String getLineNumber() { return lineNumber; }
    public void setLineNumber(String line) { this.lineNumber = line; }

    public Long getBusId() { return busId; }
    public void setBusId(Long busId) { this.busId = busId; }

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }

  
  

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getBusLicensePlate() { return busLicensePlate; }
    public void setBusLicensePlate(String busLicensePlate) { this.busLicensePlate = busLicensePlate; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
}