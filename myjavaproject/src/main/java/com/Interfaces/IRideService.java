
package com.Interfaces;



import com.Dto.RideDTO;


import java.time.LocalTime;
import java.util.List;


public interface IRideService extends ICrudService<RideDTO, Long> {
    // חישוב זמן הגעה משוער לתחנה (דקה לכל תחנה לפי האפיון)
   
    LocalTime getEstimatedArrival(String lineNumber, Long stationId);
  
    //נסיעה אחרונה ביום
      RideDTO getLastRide(String lineNumber);
    
  // מציאת מיקום קו על ציר הנסיעה
     List<String>  getBusLocationsByLineNumber(String lineNumber);
    //  נסיעות משעה מסוימת
     List<RideDTO> getUpcomingRides(LocalTime time);

   
}

