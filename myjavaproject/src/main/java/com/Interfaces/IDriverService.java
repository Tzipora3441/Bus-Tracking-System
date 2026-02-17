package com.Interfaces;



import com.Models.Driver;

public interface IDriverService extends ICrudService<Driver, Long> {
    // עדכון דירוג הנהג - ייחודי לאפיון
    void updateRating(Long driverId, double newRating);
}
