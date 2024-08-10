package com.umc.auth.repository;


import com.umc.auth.entity.AuthMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthMemberRepository extends JpaRepository<AuthMember, Long> {
    AuthMember findByUsername(String username);
    AuthMember findByUuid(UUID uuid);
}
