package com.Services;


import com.Models.Line;
import com.Models.Station;
import com.Models.LineStation;
import com.Models.LineStationKey;
import com.Dto.LineDTO;
import com.Dto.StationDTO;
import com.Interfaces.ILineService;
import com.Repository.LineRepository;
import com.Repository.StationRepository;
import com.Repository.LineStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// }
@Service
public class LineServiceImpl implements ILineService {

    @Autowired private LineRepository lineRepo;
    @Autowired private LineStationRepository lineStationRepo;
    @Autowired private StationRepository stationRepo;

    // --- (ICrudService) מימוש מתודות גנריות עם DTO ---

    @Override
    public LineDTO save(LineDTO dto) {
        Line line = new Line();
        line.setLineNumber(dto.getLineNumber());
        line.setOrigin(dto.getOrigin());
        line.setDestination(dto.getDestination());
        return convertToDTO(lineRepo.save(line));
    }

    @Override
    public List<LineDTO> findAll() {
        return lineRepo.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public LineDTO findById(Long id) {
        return lineRepo.findById(id).map(this::convertToDTO).orElse(null);
    }

    @Override
    public void delete(Long id) {
        lineRepo.deleteById(id);
    }

    @Override
    public LineDTO update(Long id, LineDTO dto) {
        Line existing = lineRepo.findById(id).orElseThrow(() -> new RuntimeException("Line not found"));
        existing.setLineNumber(dto.getLineNumber());
        existing.setOrigin(dto.getOrigin());
        existing.setDestination(dto.getDestination());
        return convertToDTO(lineRepo.save(existing));
    }

    // --- לוגיקה לניהול תחנות במסלול ---

@Override
@Transactional
public List<StationDTO> addStationToLine(String lineNumber, Long stationId, int order) {
   
    Line line = lineRepo.findByLineNumber(lineNumber).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Error: Line " + lineNumber + " not found"));

  
    Station station = stationRepo.findById(stationId)
            .orElseThrow(() -> new RuntimeException("Error: Station " + stationId + " not found"));


    if (lineStationRepo.findByLineIdAndStationId(line.getId(), stationId) != null) {
        throw new RuntimeException("Error: Station already exists in this line");
    }

    // 4. Shift existing stations
    List<LineStation> currentStations = lineStationRepo.findByLineId(line.getId());
    for (LineStation ls : currentStations) {
        if (ls.getStationOrder() >= order) {
            ls.setStationOrder(ls.getStationOrder() + 1);
        }
    }
    lineStationRepo.saveAll(currentStations);

    // 5. Create new LineStation with Composite Key
    LineStationKey key = new LineStationKey();
    key.setLineId(line.getId());
    key.setStationId(station.getId());
    
    LineStation newLineStation = new LineStation();
    newLineStation.setId(key);
    newLineStation.setLine(line);
    newLineStation.setStation(station);
    newLineStation.setStationOrder(order);

    lineStationRepo.save(newLineStation);

    // 6. RETURN the updated list using your existing getStationsByLine logic
    return getStationsByLine(lineNumber);
}
    @Override
    @Transactional
    public List<StationDTO> removeStationFromLine(String lineNumber, Long stationId) {
        Line line = lineRepo.findByLineNumber(lineNumber).stream().findFirst().orElseThrow();
        
        // 1. מחיקה
        lineStationRepo.deleteByLineIdAndStationId(line.getId(), stationId);

        // 2. סידור מחדש (Tidy Up): מוודא שאין "חורים" במספרים (1, 2, 3...)
        List<LineStation> remaining = lineStationRepo.findByLineIdOrderByStationOrderAsc(line.getId());
        for (int i = 0; i < remaining.size(); i++) {
            remaining.get(i).setStationOrder(i + 1);
        }
        lineStationRepo.saveAll(remaining);
         return getStationsByLine(lineNumber);
}
    

    @Override
    public List<StationDTO> getStationsByLine(String lineNumber) {
        Line line = lineRepo.findByLineNumber(lineNumber).stream().findFirst().orElseThrow();
        return lineStationRepo.findByLineIdOrderByStationOrderAsc(line.getId()).stream()
                .map(ls -> {
                    StationDTO sDto = new StationDTO();
                    sDto.setId(ls.getStation().getId());
                    sDto.setName(ls.getStation().getName());
                    return sDto;
                }).toList();
    }

    // --- Mappers (פונקציות המרה) ---

    private LineDTO convertToDTO(Line line) {
    LineDTO dto = new LineDTO();
    dto.setId(line.getId());
    dto.setLineNumber(line.getLineNumber());
    dto.setOrigin(line.getOrigin());
    dto.setDestination(line.getDestination());
        return dto;
    }

    @Override
    public List<LineDTO> findLinesByStation(Long stationId) {
        return lineRepo.findByLineStationsStationId(stationId).stream().map(this::convertToDTO).toList();
    }

    @Override
    public List<LineDTO> findByLineNumber(String lineNumber) {
        return lineRepo.findByLineNumber(lineNumber).stream().map(this::convertToDTO).toList();
    }
     @Override
    public List<LineDTO> findByDestination(String destination) {
        return lineRepo.findByDestination(destination).stream().map(this::convertToDTO).toList();
    }
}