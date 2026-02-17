package com.Models;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;

@Entity
@Table(name = "lines")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // קוד קו (PK)

    @Column(nullable = false)
    private String lineNumber; // מספר הקו (למשל "422")

    private String origin; // מוצא
    private String destination; // יעד

    // קשר לרשימת התחנות במסלול (טבלת העזר)
    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("stationOrder ASC") // מבטיח שהתחנות יחזרו לפי הסדר במסלול
    private List<LineStation> lineStations = new ArrayList<>();

    // קשר לנסיעות של הקו
    @OneToMany(mappedBy = "line")
    @JsonManagedReference
    private List<Ride> rides;

    public Line() {
    }

    public Line(String lineNumber, String origin, String destination) {
        this.lineNumber = lineNumber;
        this.origin = origin;
        this.destination = destination;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLineNumber() { return lineNumber; }
    public void setLineNumber(String lineNumber) { this.lineNumber = lineNumber; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public List<LineStation> getLineStations() { return lineStations; }
    public void setLineStations(List<LineStation> lineStations) { this.lineStations = lineStations; }
}