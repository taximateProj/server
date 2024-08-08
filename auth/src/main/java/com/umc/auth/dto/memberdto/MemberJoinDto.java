package com.umc.auth.dto.memberdto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberJoinDto {
    private String name;
    private String school;
    private String accountNumber;
}
