package com.Repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Models.Line;



@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
    // חיפוש קו לפי המספר שלו (למשל "422")
     List<Line> findByLineNumber(String lineNumber);
    // שליפה לפי יעד
    List<Line> findByDestination(String destination);
    
    // שאילתה שמוצאת קווים לפי ID של תחנה שנמצאת ברשימת ה-lineStations שלהם
    List<Line> findByLineStationsStationId(Long stationId);
    

}