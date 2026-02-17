package com.Interfaces;



import com.Dto.BusDTO;


public interface IBusService extends ICrudService<BusDTO, Long> {
    // חיפוש אוטובוס לפי לוחית רישוי ן
    BusDTO findByLicensePlate(String licenseNumber);
}