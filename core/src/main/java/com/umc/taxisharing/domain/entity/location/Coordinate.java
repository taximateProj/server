package com.umc.taxisharing.domain.entity.location;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Coordinate {
    private double latitude;
    private double longitude;

    @Builder
    public Coordinate(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateCoordinate(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
