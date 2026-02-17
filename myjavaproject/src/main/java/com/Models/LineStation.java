package com.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "line_stations")
public class LineStation {

    @EmbeddedId
    private LineStationKey id; // המפתח המורכב שיצרת קודם

    // הקשר לקו - MapsId אומר ל-JPA לקחת את ה-lineId מהמפתח המורכב
    @ManyToOne
    @MapsId("lineId")
    @JoinColumn(name = "line_id")
    @JsonBackReference
    private Line line;

    // הקשר לתחנה - MapsId אומר ל-JPA לקחת את ה-stationId מהמפתח המורכב
    @ManyToOne
    @MapsId("stationId")
    @JoinColumn(name = "station_id")
    @JsonIgnoreProperties("lineStations")
    private Station station;

    @Column(nullable = false)
    private int stationOrder; // המיקום של התחנה במסלול (1, 2, 3...)

    // 1. קונסטרקטור ריק חובה ל-JPA
    public LineStation() {
    }

    // 2. קונסטרקטור נוחות להקמה מהירה
    public LineStation(Line line, Station station, int stationOrder) {
        this.line = line;
        this.station = station;
        this.stationOrder = stationOrder;
        // חשוב: מאתחלים את המפתח המורכב עם ה-IDs של האובייקטים
        this.id = new LineStationKey(line.getId(), station.getId());
    }

    // 3. Getters and Setters
    public LineStationKey getId() { return id; }
    public void setId(LineStationKey id) { this.id = id; }

    public Line getLine() { return line; }
    public void setLine(Line line) { this.line = line; }

    public Station getStation() { return station; }
    public void setStation(Station station) { this.station = station; }

    public int getStationOrder() { return stationOrder; }
    public void setStationOrder(int stationOrder) { this.stationOrder = stationOrder; }
}