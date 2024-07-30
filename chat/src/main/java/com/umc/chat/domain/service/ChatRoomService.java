package com.umc.chat.domain.service;

import com.umc.chat.domain.entity.ChatRoom;
import com.umc.chat.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;


    public void createChatRoom(String roomId) {
        // 코어 서버로부터 받은 택시 합승방 ID를 사용하여 채팅방 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .build();
        chatRoomRepository.save(chatRoom);
    }

    public void deleteChatRoom(String roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
