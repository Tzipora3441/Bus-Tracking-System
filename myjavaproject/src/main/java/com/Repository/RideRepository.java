package com.Repository;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Models.Ride;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
  

    // לחיפוש נסיעות בשעה מסוימת (או אחריה)
    List<Ride> findByDepartureTimeAfter(LocalTime time);
    List<Ride> findByLineIdAndDepartureTimeAfter(Long lineId, LocalTime time);
    // מוצא את הנסיעה הקרובה ביותר שיוצאת אחרי זמן מסוים
    Ride findFirstByLineIdAndDepartureTimeAfterOrderByDepartureTimeAsc(Long lineId, LocalTime time);
    
    // למציאת הנסיעה האחרונה ביום (לפי קו) - תוקן להוסיף פרמטר time
    Ride findFirstByLineIdAndDepartureTimeAfterOrderByDepartureTimeDesc(Long lineId, LocalTime time);
}