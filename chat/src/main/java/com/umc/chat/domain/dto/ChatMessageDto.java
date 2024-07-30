package com.umc.chat.domain.dto;

import com.umc.chat.domain.entity.ChatMessage;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageDto {
    @NotEmpty
    private String roomId;
    @NotEmpty
    private String sender;
    @NotEmpty
    private String content;

    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(sender)
                .content(content)
                .build();
    }

    @Builder
    public ChatMessageDto(String roomId, String sender, String content) {
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
    }
}