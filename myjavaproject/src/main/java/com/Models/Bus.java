package com.Models;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // קוד אוטובוס (PK)

    @Column(nullable = false, unique = true)
    private String licensePlate; // לוחית רישוי (ייחודית)

    private int capacity; // מקומות (קיבולת)

    // קשר לנסיעות שהאוטובוס מבצע
    @OneToMany(mappedBy = "bus")
    
    private List<Ride> rides;

    // קונסטרקטור ריק חובה ל-JPA
    public Bus() {
    }

    public Bus(String licensePlate, int capacity) {
        this.licensePlate = licensePlate;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Ride> getTrips() { return rides; }
    public void setTrips(List<Ride> trips) { this.rides = trips; }
}
