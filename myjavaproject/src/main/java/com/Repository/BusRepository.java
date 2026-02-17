package com.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dto.BusDTO;
import com.Models.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    //  מצא את אוטובוס לפי לוחית רישוי
    Bus findByLicensePlate(String licensePlate);
}