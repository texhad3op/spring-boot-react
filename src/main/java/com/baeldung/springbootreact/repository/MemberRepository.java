package com.baeldung.springbootreact.repository;

import com.baeldung.springbootreact.domain.Member;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends R2dbcRepository<Member, Long> {
    Mono<Member> findByName(String name);
}
