package com.Services;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dto.BusDTO;
import com.Interfaces.IBusService;
import com.Models.Bus;
import com.Repository.BusRepository;

@Service
public class BusServiceImpl implements IBusService {

    @Autowired
    private BusRepository busRepo;

    // --- מימוש CRUD (מהממשק הגנרי) ---|
    @Override
    public BusDTO save(BusDTO dto) {
        // Step 1: Convert DTO to Entity so the Repository can save it
        Bus bus = convertToEntity(dto);
        // Step 2: Save and convert the result back to DTO
        return convertToDTO(busRepo.save(bus));
    }

public List<BusDTO> findAll() {
        List<Bus> buses = busRepo.findAll();
        return buses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
    }

 @Override
    public BusDTO findById(Long id) {
        return busRepo.findById(id)
                      .map(this::convertToDTO)
                      .orElse(null);
    }

    @Override
    public BusDTO update(Long id, BusDTO dto) {
        Bus existingBus = busRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        
        existingBus.setLicensePlate(dto.getLicensePlate());
        existingBus.setCapacity(dto.getCapacity());
        
        
        return convertToDTO(busRepo.save(existingBus));
    }

   @Override
    public void delete(Long id) {
        busRepo.deleteById(id);
    }
    // --- חיפושים וניהול (מ-IBusService) ---

    @Override
    public BusDTO findByLicensePlate(String licensePlate) {
        Bus b=busRepo.findByLicensePlate(licensePlate);
        return convertToDTO(b);
    }
    
    

    private BusDTO convertToDTO(Bus bus) {
        BusDTO dto = new BusDTO();
        dto.setId(bus.getId());
        dto.setLicensePlate(bus.getLicensePlate());
        dto.setCapacity(bus.getCapacity());
     
        return dto;
    }
    private Bus convertToEntity(BusDTO dto) {
        Bus bus = new Bus();
        // Don't set the ID manually for new saves if it's Auto-Incremented
        if (dto.getId() != null) bus.setId(dto.getId()); 
        bus.setLicensePlate(dto.getLicensePlate());
        bus.setCapacity(dto.getCapacity());
      
        return bus;
    }
}