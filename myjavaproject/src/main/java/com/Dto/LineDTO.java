package com.Dto;


public class LineDTO {
    private Long id;
    private String lineNumber; // המשתנה קיים
    private String origin;
    private String destination;
    // private List<StationDTO> stations;

    // חובה להוסיף את המתודה הזו:
    public String getLineNumber() {
        return lineNumber;
    }

    // וגם את זו כדי שנוכל לעדכן:
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    // Getters & Setters לשאר השדות
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // public List<StationDTO> getStations() { return stations; }
    // public void setStations(List<StationDTO> stations) { this.stations = stations; }
}