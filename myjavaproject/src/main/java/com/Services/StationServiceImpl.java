package com.Services;

import com.Models.Station;
import com.Dto.LineDTO;
import com.Dto.StationDTO;
import com.Interfaces.ILineService;
import com.Interfaces.IStationService;
import com.Repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationServiceImpl implements IStationService {

    @Autowired
    private StationRepository stationRepo;
    
    @Autowired
    private ILineService lineService;

    // --- מימוש CRUD (עבודה מול DTO) ---

    @Override
    public StationDTO save(StationDTO dto) {
        // 1. המרה מ-DTO לישות לצורך שמירה
        Station station = new Station();
        station.setName(dto.getName());

        // 2. שמירה במסד הנתונים (ה-ID נוצר כאן)
        Station saved = stationRepo.save(station);

        // 3. החזרת DTO עם המזהה החדש
        return convertToDTO(saved);
    }

    @Override
    public List<StationDTO> findAll() {
        // שליפת כל הישויות והמרתן לרשימת DTO
        return stationRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StationDTO findById(Long id) {
        // חיפוש תחנה והמרה ל-DTO אם נמצאה
        return stationRepo.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public StationDTO update(Long id, StationDTO dto) {
        // עדכון תחנה קיימת
        Station existing = stationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("תחנה לא נמצאה"));
        
        existing.setName(dto.getName());
        Station updated = stationRepo.save(existing);
        
        return convertToDTO(updated);
    }

    @Override
    public void delete(Long id) {
        // מחיקת תחנה לפי מזהה
        stationRepo.deleteById(id);
    }

    // --- לוגיקה עסקית ---

    @Override
    public List<LineDTO> findLinesByStationId(Long id) {
        // שליפת קווים המשויכים לתחנה דרך ה-LineService
        return lineService.findLinesByStation(id);
    }

    // פונקציית עזר להמרת ישות ל-DTO
    private StationDTO convertToDTO(Station station) {
        StationDTO dto = new StationDTO();
        dto.setId(station.getId());
        dto.setName(station.getName());
        return dto;
    }
}