package com.Interfaces;

import com.Dto.LineDTO;
import com.Dto.StationDTO;
import java.util.List;

public interface ILineService extends ICrudService<LineDTO, Long> {
    // הוספת תחנה לפי מספר קו וסידור מחדש
    List<StationDTO> addStationToLine(String lineNumber, Long stationId, int order);
    
    // הסרת תחנה לפי מספר קו וסידור מחדש (Tidy Up)
    List<StationDTO> removeStationFromLine(String lineNumber, Long stationId);
// מציאת קווים בתחנה
    List<LineDTO> findLinesByStation(Long stationId);
    //מציאת פרטי הקו
   List<LineDTO> findByLineNumber(String lineNumber);
// מציאת תחנות הקו
    List<StationDTO> getStationsByLine(String lineNumber);
    // מציאה קווים לפי יעד
    List<LineDTO> findByDestination(String destination);
}