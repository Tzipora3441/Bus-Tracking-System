package com.Controllers;

import com.Dto.BusDTO;

import com.Services.BusServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusServiceImpl busService;

    // שליפת כל האוטובוסים
    @GetMapping("/all")
    public List<BusDTO> getAllBuses() {
        return busService.findAll();
    }
    @GetMapping("/plate/{plate}")
    public BusDTO getBusByPlate(  @PathVariable String plate) {
      
        return busService.findByLicensePlate(plate);
    }
    // הוספת אוטובוס חדש
    @PostMapping("/add")
    public BusDTO saveBus(@RequestBody BusDTO bus) {
        return busService.save(bus);
    }

}
