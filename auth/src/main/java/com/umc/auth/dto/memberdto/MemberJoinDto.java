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
    private String gender;

    @Builder
    public MemberJoinDto(String name, String school, String accountNumber, String gender) {
        this.name = name;
        this.school = school;
        this.accountNumber = accountNumber;
        this.gender = gender;
    }
}
