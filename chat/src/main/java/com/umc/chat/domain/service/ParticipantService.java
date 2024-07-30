package com.umc.chat.domain.service;

import com.umc.chat.domain.entity.Participant;
import com.umc.chat.domain.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public void createParticipant(String roomId, Long memberId){
        Participant newParticipant = Participant.builder()
                .roomId(roomId)
                .memberId(memberId)
                .build();
        participantRepository.save(newParticipant);
    }

    public void deleteParticipant(String roomId, Long memberId){
        participantRepository.deleteByRoomIdAndMemberId(roomId, memberId);
    }

    public void deleteParticipantsByRoomId(String roomId) {
        List<Participant> participants = participantRepository.findByRoomId(roomId);
        participantRepository.deleteAll(participants);
    }
}
