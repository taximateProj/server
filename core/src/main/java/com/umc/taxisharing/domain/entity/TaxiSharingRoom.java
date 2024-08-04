package com.umc.taxisharing.domain.entity;

import com.umc.taxisharing.domain.entity.location.Location;
import com.umc.taxisharing.domain.entity.money.Money;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "taxiSharingRooms")
public class TaxiSharingRoom {
    @Id
    private ObjectId id;

    private String roomName;

    private Location departureLocation;
    private Location arriavalLocation;
    private LocalDate departureDate;
    private LocalTime departureTime;

    private final List<Participant> participants;
    private Long hostMemberId;

    private Integer maxParticipantNumber;

    private Money estimatedCost;
    private Money totalCost;


    public TaxiSharingRoom(String roomName, Location departureLocation, Location arriavalLocation, LocalDate departureDate, LocalTime departureTime, Integer maxParticipantNumber,Money estimatedCost){
        this.roomName = roomName;
        this.departureLocation = departureLocation;
        this.arriavalLocation = arriavalLocation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.participants = new ArrayList<>();
        this.maxParticipantNumber = maxParticipantNumber;
        this.estimatedCost = estimatedCost;
    }

    public void updateEstimatedCost(Money estimatedCost){
        this.estimatedCost = estimatedCost;
    }
    public void updateRoomName(String newName){this.roomName = newName;}

    public void setTotalCost(Money totalCost){
        this.totalCost = totalCost;
    }

    public void initiateTaxiSharingRoom(Participant hostParticipant, Long hostMemberId){
        this.participants.add(hostParticipant);
        this.hostMemberId = hostMemberId;
    }

    public void addParticipant(Participant participant){
        this.participants.add(participant);
    }

    public void removeParticipant(Participant participant){
        this.participants.remove(participant);
    }

}
