package com.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.Models.LineStation;
import com.Models.LineStationKey;
import com.Models.Ride;

import jakarta.transaction.Transactional;

@Repository
public interface LineStationRepository extends JpaRepository<LineStation, LineStationKey> {
    // למציאת כל התחנות של קו מסוים מסודרות לפי סדר התחנה
   //    Optional findByLineLineNumberAndStationId(String lineNumber, Long stationId);
     // ללוחות זמנים: כל הנסיעות של קו מסוים
    // List<Ride> findByLineLineNumber(String lineNumber);
    // למציאת תחנה ספציפית בתוך קו (עבור פונקציית "מתי הקו יגיע")
     LineStation findByLineIdAndStationId(Long lineId, Long stationId);
    @Transactional
    @Modifying
    void deleteByLineIdAndStationId(Long lineId, Long stationId);
    List<LineStation> findByLineIdOrderByStationOrderAsc(Long lineId);
    List<LineStation> findByLineId(Long lineId);
}