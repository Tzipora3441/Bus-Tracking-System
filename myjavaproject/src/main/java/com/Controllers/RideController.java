
package com.Controllers;

import com.Dto.RideDTO;
import com.Interfaces.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private IRideService rideService;
  
    // 1. קבלת כל הנסיעות המתוכננות
    @GetMapping
    public List<RideDTO> getAllRides() {
        return rideService.findAll();
    }

    // 2. יצירת נסיעה חדשה (שיבוץ אוטובוס ונהג לקו)
@PostMapping
    public ResponseEntity<RideDTO> createRide(@RequestBody RideDTO dto) {
        // 1. Save the ride using the Service
        RideDTO ride = rideService.save(dto); 
        
        // 2. Create a NEW instance of the Response DTO (Don't use Autowired!)
       RideDTO response = new RideDTO();
        
        // 3. Map the values from the saved 'ride' to the 'response'
        // response.setId(ride.getId());
        response.setBusId(ride.getBusId());
        response.setDriverId(ride.getDriverId());
        response.setLineNumber(ride.getLineNumber());
        response.setDepartureTime(ride.getDepartureTime());
        
        return ResponseEntity.ok(response);
    }

    // 3. קבלת נסיעה ספציפית לפי ID
    @GetMapping("/{id}")
    public ResponseEntity<RideDTO> getRideById(@PathVariable Long id) {
        RideDTO ride = rideService.findById(id);
        if (ride != null) {
            return ResponseEntity.ok(ride);
        }
        return ResponseEntity.notFound().build();
    }

    // 4. הפונקציה המרכזית: מתי האוטובוס יגיע לתחנה שלי?
    // דוגמה לקריאה: /api/rides/line/1/station/5/arrival
 @GetMapping("/line/{lineNumber}/station/{stationId}/arrival")
public ResponseEntity<?> getEstimatedArrivalTime(
        @PathVariable String lineNumber, 
        @PathVariable Long stationId) {
    
    try {
        LocalTime arrivalTime = rideService.getEstimatedArrival(lineNumber, stationId);
        return ResponseEntity.ok(arrivalTime);
    } catch (RuntimeException e) {
        // This will return a 404 error with your Hebrew message instead of a 500 crash
        return ResponseEntity.status(404).body(e.getMessage());
    }
}

    // 5. מחיקת נסיעה
 @DeleteMapping("/{id}")
public ResponseEntity<Long> deleteRide(@PathVariable Long id) {
    rideService.delete(id);
    return ResponseEntity.ok(id); // Sends Status 200 + the ID
}
    //מציאת מיקום הקו לאורך ציר הנסיעה
    @GetMapping("/line/{lineNumber}/location")
    public List<String> getBusLocationsByLineNumber(String lineNumber){
      return  rideService.getBusLocationsByLineNumber( lineNumber);
    }
    
    // שליפת הנסיעה האחרונה של קו ספציפי לפי מספר קו
@GetMapping("/line/{lineNumber}/last")
public ResponseEntity<RideDTO> getLastRide(@PathVariable String lineNumber) {
    RideDTO lastRide = rideService.getLastRide(lineNumber); // קריאה לפונקציה שכבר בנית
    
    if (lastRide != null) {
        return ResponseEntity.ok(lastRide);
    }
    return ResponseEntity.notFound().build();
}
// 
@GetMapping("/upcoming")
    public ResponseEntity<List<RideDTO>> getUpcomingRides(@RequestParam String time) {
        LocalTime searchTime = LocalTime.parse(time);
        List<RideDTO> upcoming = rideService.getUpcomingRides(searchTime);
        return ResponseEntity.ok(upcoming);
    }
}