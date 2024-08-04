package com.umc.taxisharing.domain.entity.location;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Location {
    private String name;
    private Coordinate coordinate;

    @Builder
    public Location(String name, double latitude, double longitude){
        this.name = name;
        this.coordinate = Coordinate.builder()
                .latitude(latitude)
                .longitude(longitude)
                        .build();
    }

    public Location(String name, Coordinate coordinate){
        this.name = name;
        this.coordinate = coordinate;
    }
}
