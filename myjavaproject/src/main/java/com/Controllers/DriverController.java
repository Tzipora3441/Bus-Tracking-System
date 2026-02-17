package com.Controllers;


import com.Models.Driver;
import com.Services.DriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverServiceImpl driverService;

    // שליפת כל הנהגים
    @GetMapping("/all")
    public List<Driver> getAllDrivers() {
        return driverService.findAll();
    }
    // שליפת נהג לפי מזהה (ID)
    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return driverService.findById(id);
    }
    // הוספת נהג חדש
    @PostMapping("/add")
    public Driver saveDriver(@RequestBody Driver driver) {
        return driverService.save(driver);
    }
// עדכון נהג קיים
    @PatchMapping("/{id}/rating")
    public ResponseEntity<String> updateDriverRating(
            @PathVariable Long id, 
            @RequestParam double rating) {
        
        // קריאה לשירות לעדכון הדירוג
        driverService.updateRating(id, rating);
        
        // החזרת הודעת הצלחה
        return ResponseEntity.ok("הדירוג של נהג מספר " + id + " עודכן בהצלחה ל-" + rating);
    }

}
