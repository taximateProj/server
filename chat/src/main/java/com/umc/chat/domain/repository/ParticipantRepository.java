package com.umc.chat.domain.repository;

import com.umc.chat.domain.entity.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends MongoRepository<Participant, String> {
    List<Participant> findByRoomId(String roomId);

    void deleteByRoomIdAndMemberId(String roomId, Long memberId);
}
