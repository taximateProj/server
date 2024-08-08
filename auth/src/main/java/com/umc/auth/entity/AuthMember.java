package com.umc.auth.entity;

import com.umc.member.domain.common.BaseEntity;
import com.umc.member.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "BINARY(16)",nullable = false, updatable = false)
    private UUID uuid; // 생성하기

    private String username; // 카카오에서 온 정보는 이걸로 확인

    private String email;

    private Role role;

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID();
    }



}

