package com.Controllers;


import com.Dto.LineDTO;
import com.Dto.StationDTO;


import com.Services.LineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lines")
public class LineController {

    @Autowired
    private LineServiceImpl lineService;

    @GetMapping
    public List<LineDTO> getAllLines() {
        return lineService.findAll();
    }

    @PostMapping
    public LineDTO addLine(@RequestBody LineDTO line) {
        return lineService.save(line);
    }

    // חיפוש חכם: לפי מספר קו או לפי יעד
    // דוגמה: /api/lines/search?number=422
    @GetMapping("/search")
    public List<LineDTO> searchLines(
            @RequestParam(required = false) String number,
            @RequestParam(required = false) String destination) {
        if (number != null) return lineService.findByLineNumber(number);
         if (destination != null) return lineService.findByDestination(destination);
        return lineService.findAll();
    }

// הוספת תחנה למספר קו
@PostMapping("/{lineNumber}/stations")
public ResponseEntity<List<StationDTO>> addStationToLine(
        @PathVariable String lineNumber, 
        @RequestParam Long stationId,
        @RequestParam(name = "order") int order) { 
    
    // 1. Perform the action
    lineService.addStationToLine(lineNumber, stationId, order);
    
    // 2. Return the updated list (Excellent UX!)
    List<StationDTO> updatedStations = lineService.getStationsByLine(lineNumber);
    return ResponseEntity.ok(updatedStations);
}
// מחיקת תחנה
@DeleteMapping("/{lineNumber}/station/{stationId}")
public void removeStationFromLine(
        @PathVariable("lineNumber") String lineINumber, 
        @PathVariable("stationId") Long stationId) { 
    
    // השורה הזו משתמשת במשתנים, ולכן הצבע שלהם יהפוך לכהה/רגיל
    lineService.removeStationFromLine(lineINumber, stationId);
}


@GetMapping("/{lineNumber}/stations")
public ResponseEntity<List<StationDTO>> getStationsByLine(@PathVariable String lineNumber) {
    List<StationDTO> stations = lineService.getStationsByLine(lineNumber);
    return ResponseEntity.ok(stations);
}
}
