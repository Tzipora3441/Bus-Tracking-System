package com.Models;

import jakarta.persistence.*;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // קוד נסיעה (PK)

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    @JsonBackReference
    private Bus bus; // קוד אוטובוס (FK)

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    
    private Driver driver; // קוד נהג (FK)

    @ManyToOne
    @JoinColumn(name = "line_id", nullable = false)
    @JsonBackReference
    private Line line; // קוד קו (FK)

    @Column(nullable = false)
    private LocalTime departureTime; // זמן יציאה

    // קונסטרקטור ריק חובה ל-JPA
    public Ride() {
    }

    public Ride(Bus bus, Driver driver, Line line, LocalTime departureTime) {
        this.bus = bus;
        this.driver = driver;
        this.line = line;
        this.departureTime = departureTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }
}
