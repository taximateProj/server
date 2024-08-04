package com.umc.taxisharing.domain.entity.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class BankAccount {
    @Id
    private Long id;

    private String bank;
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}
