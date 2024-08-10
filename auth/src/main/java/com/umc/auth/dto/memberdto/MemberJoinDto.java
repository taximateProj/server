package com.umc.auth.dto.memberdto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberJoinDto {

    private String name;
    private String school;
    private String accountNumber;

    @Builder
    public MemberJoinDto(String name, String school, String accountNumber) {
        this.name = name;
        this.school = school;
        this.accountNumber = accountNumber;
    }
}
