package com.Models;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;

@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // קוד תחנה (PK)

    @Column(nullable = false)
    private String name; // שם התחנה

    // קשר לרשימת הקווים שעוברים בתחנה זו (דרך טבלת העזר)
    @OneToMany(mappedBy = "station")
    @JsonBackReference
    private List<LineStation> lineStations = new ArrayList<>();

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<LineStation> getLineStations() { return lineStations; }
    public void setLineStations(List<LineStation> lineStations) { this.lineStations = lineStations; }
}