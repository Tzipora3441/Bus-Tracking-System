package com.Services;

import com.Models.Driver;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.Interfaces.IDriverService;
import com.Repository.DriverRepository;

@Service
public class DriverServiceImpl implements IDriverService {

    @Autowired
    private DriverRepository driverRepo;

    // --- מימוש CRUD (מהממשק הגנרי) ---

    @Override
    public Driver save(Driver driver) {
        return driverRepo.save(driver);
    }

    @Override
    public List<Driver> findAll() {
        return driverRepo.findAll();
    }

    @Override
    public Driver findById(Long id) {
        return driverRepo.findById(id).orElse(null);
    }

    @Override
    public Driver update(Long id, Driver driverDetails) {
        Driver existing = driverRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        
        // עדכון השדות מהאפיון
        existing.setName(driverDetails.getName());
        existing.setPhone(driverDetails.getPhone());
        // שימי לב: אנחנו לא מעדכנים דירוג כאן, אלא בפונקציה ייעודית
        
        return driverRepo.save(existing);
    }

    @Override
    public void delete(Long id) {
        driverRepo.deleteById(id);
    }

    // --- לוגיקה ניהולית (מ-IDriverService) ---

    @Override
    public void updateRating(Long driverId, double newRating) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setRating(newRating);
        driverRepo.save(driver);
    }


}