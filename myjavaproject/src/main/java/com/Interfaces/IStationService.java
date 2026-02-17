package com.Interfaces;

import java.util.List;

import com.Dto.LineDTO;
import com.Dto.StationDTO;


// עדכון הממשק להשתמש ב-DTO במקום בישות ישירות
public interface IStationService extends ICrudService<StationDTO, Long> {
    
    // שליפת כל הקווים העוברים בתחנה מסוימת
    List<LineDTO> findLinesByStationId(Long id);
}