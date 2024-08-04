package com.umc.chat.domain.repository;

import com.umc.chat.domain.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String>{
    void deleteById(String roomId);
}
