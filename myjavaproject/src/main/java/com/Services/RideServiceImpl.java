package com.Services;

import com.Models.*;
import com.Dto.RideDTO;
import com.Interfaces.IRideService;
import com.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideServiceImpl implements IRideService {

    @Autowired private RideRepository rideRepo;
    @Autowired private LineRepository lineRepo;
    @Autowired private LineStationRepository lineStationRepo;
    @Autowired private BusRepository busRepo;
    @Autowired private DriverRepository driverRepo;

    // --- (ICrudService) מימוש מתודות גנריות ---
    @Override
    public RideDTO save(RideDTO dto) {
     Ride ride = new Ride();
    ride.setDepartureTime(dto.getDepartureTime());

    // 1. שליפת הקו לפי מספר קו (String) במקום ID
    Line line = lineRepo.findByLineNumber(dto.getLineNumber()).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("שגיאה: קו מספר " + dto.getLineNumber() + " לא נמצא"));
    ride.setLine(line);

    // 2. שליפת האוטובוס
    Bus bus = busRepo.findById(dto.getBusId())
            .orElseThrow(() -> new RuntimeException("שגיאה: אוטובוס לא נמצא"));
    ride.setBus(bus);

    // 3. שליפת הנהג
    Driver driver = driverRepo.findById(dto.getDriverId())
            .orElseThrow(() -> new RuntimeException("שגיאה: נהג לא נמצא"));
    ride.setDriver(driver);

    return convertToDTO(rideRepo.save(ride));
}

  

    @Override
    public List<RideDTO> findAll() {
        // החזרת כל הנסיעות כ-DTO
        return rideRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

@Override
public RideDTO findById(Long id) {
    return rideRepo.findById(id)
            .map(this::convertToDTO) // שימוש ב-convertToDTO החדש שיצרנו
            .orElseThrow(() -> new RuntimeException("נסיעה לא נמצאה"));
}

@Override
public RideDTO update(Long id, RideDTO dto) {
    Ride existingRide = rideRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("שגיאה: נסיעה לא נמצאה"));

    existingRide.setDepartureTime(dto.getDepartureTime());

    // עדכון הקו לפי מספר קו
    Line line = lineRepo.findByLineNumber(dto.getLineNumber()).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("שגיאה: קו מספר " + dto.getLineNumber() + " לא נמצא"));
    existingRide.setLine(line);

    // עדכון אוטובוס ונהג
    existingRide.setBus(busRepo.findById(dto.getBusId()).orElseThrow(() -> new RuntimeException("אוטובוס לא נמצא")));
    existingRide.setDriver(driverRepo.findById(dto.getDriverId()).orElseThrow(() -> new RuntimeException("נהג לא נמצא")));

    return convertToDTO(rideRepo.save(existingRide));
}
    @Override
    public void delete(Long id) {
    if (rideRepo.existsById(id)) {
        
        rideRepo.deleteById(id);
        
    } else {
        throw new RuntimeException("Cannot delete: Ride with ID " + id + " not found.");
    }
}
    

    // --- לוגיקה עסקית ("קל קו") ---

    @Override
    public LocalTime getEstimatedArrival(String lineNumber, Long stationId) {
        Line line = lineRepo.findByLineNumber(lineNumber).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("קו לא נמצא"));

        Ride ride = rideRepo.findFirstByLineIdAndDepartureTimeAfterOrderByDepartureTimeAsc(line.getId(), LocalTime.now());
        if (ride == null) throw new RuntimeException("אין נסיעות פעילות כרגע");

        LineStation ls = lineStationRepo.findByLineIdAndStationId(line.getId(), stationId);
        int minutesToAdd = ls.getStationOrder() - 1; 
        return ride.getDepartureTime().plusMinutes(minutesToAdd);
    }

    @Override
    public List<String> getBusLocationsByLineNumber(String lineNumber) {
        Line line = lineRepo.findByLineNumber(lineNumber).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("קו לא נמצא"));

        LocalTime anHourAgo = LocalTime.now().minusMinutes(60);
        List<Ride> activeRides = rideRepo.findByLineIdAndDepartureTimeAfter(line.getId(), anHourAgo);

        return activeRides.stream().map(ride -> {
            long minutesPassed = java.time.Duration.between(ride.getDepartureTime(), LocalTime.now()).toMinutes();
            int currentOrder = (int) minutesPassed + 1;

            return ride.getLine().getLineStations().stream()
                    .filter(ls -> ls.getStationOrder() == currentOrder)
                    .map(ls -> "אוטובוס של שעה " + ride.getDepartureTime() + " נמצא ב: " + ls.getStation().getName())
                    .findFirst()
                    .orElse("אוטובוס סיים מסלול");
        }).toList();
    }

    @Override
    public List<RideDTO> getUpcomingRides(LocalTime time) {
        return rideRepo.findByDepartureTimeAfter(time).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


// לוחות זמנים - הנסיעה האחרונה המתוכננת להיום עבור קו מסוים
    @Override
    public RideDTO getLastRide(String lineNumber) {
        // 1. מציאת הקו לפי המספר שלו
        Line line = lineRepo.findByLineNumber(lineNumber).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("קו לא נמצא"));

        // 2. שליפת הנסיעה האחרונה מהרפוזיטורי (לפי שעת יציאה יורדת)
        Ride lastRide = rideRepo.findFirstByLineIdAndDepartureTimeAfterOrderByDepartureTimeDesc(
                line.getId(), 
                LocalTime.MIN
        );

        // 3. המרה ל-DTO והחזרה
        return (lastRide != null) ? convertToDTO(lastRide) : null;
    }
    // --- פונקציות המרה (Mappers) ---

private RideDTO convertToDTO(Ride ride) {
    RideDTO dto = new RideDTO();
    dto.setId(ride.getId());
    dto.setDepartureTime(ride.getDepartureTime());

    if (ride.getLine() != null) {
        // כאן אנחנו מחזירים את מספר הקו ל-DTO
        dto.setLineNumber(ride.getLine().getLineNumber()); 
        dto.setOrigin(ride.getLine().getOrigin());
        dto.setDestination(ride.getLine().getDestination());
    }

    if (ride.getBus() != null) {
        dto.setBusId(ride.getBus().getId());
        dto.setBusLicensePlate(ride.getBus().getLicensePlate());
    }

    if (ride.getDriver() != null) {
        dto.setDriverId(ride.getDriver().getId());
        // תיקון: הצגת השם המלא של הנהג
        dto.setDriverName(ride.getDriver().getName()); 
    }

    return dto;
}

    private Ride convertToEntity(RideDTO dto) {
        Ride ride = new Ride();
        ride.setId(dto.getId());
        ride.setDepartureTime(dto.getDepartureTime());
        ride.setBus(busRepo.findById(dto.getBusId()).orElseThrow());
        ride.setDriver(driverRepo.findById(dto.getDriverId()).orElseThrow());
        ride.setLine(lineRepo.findByLineNumber(dto.getLineNumber()).stream().findFirst().orElseThrow());
        return ride;
    }
}