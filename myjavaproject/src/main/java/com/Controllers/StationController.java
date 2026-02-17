package com.Controllers;


import com.Dto.LineDTO;
import com.Dto.StationDTO;
import com.Interfaces.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    @Autowired
    private IStationService stationService; // השתמש בממשק להזרקה

    @GetMapping("/all")
    public List<StationDTO> getAllStations() {
        // החזרת כל התחנות בפורמט DTO
        return stationService.findAll();
    }

    @PostMapping("/add")
    public StationDTO addStation(@RequestBody StationDTO dto) {
        // יצירת תחנה חדשה והחזרת הנתונים כולל ה-ID
        return stationService.save(dto);
    }

    @GetMapping("/{id}/lines")
    public List<LineDTO> getLinesByStation(@PathVariable Long id) {
        // שליפת קווים העוברים בתחנה לפי מזהה
        return stationService.findLinesByStationId(id);
    }
}