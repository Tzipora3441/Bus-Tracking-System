
package com.Models;




import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable // אנוטציה שאומרת שניתן להשתמש במחלקה זו כחלק ממחלקה אחרת
public class LineStationKey implements Serializable {

    private Long lineId;
    private Long stationId;

    // 1. קונסטרקטור ריק (חובה)
    public LineStationKey() {}

    // 2. קונסטרקטור נוחות
    public LineStationKey(Long lineId, Long stationId) {
        this.lineId = lineId;
        this.stationId = stationId;
    }

    // 3. Getters & Setters
    public Long getLineId() { return lineId; }
    public void setLineId(Long lineId) { this.lineId = lineId; }

    public Long getStationId() { return stationId; }
    public void setStationId(Long stationId) { this.stationId = stationId; }

    // 4. מימוש equals ו-hashCode (חובה למפתח מורכב כדי ש-JPA יוכל להשוות בין שורות)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineStationKey that = (LineStationKey) o;
        return Objects.equals(lineId, that.lineId) && 
               Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineId, stationId);
    }
}
