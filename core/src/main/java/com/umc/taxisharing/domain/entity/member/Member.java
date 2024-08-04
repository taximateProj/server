package com.umc.taxisharing.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String nickname;
    private String email;
    private Gender gender;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;

    @Builder
    public Member(String name, String email, Gender gender){
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
}