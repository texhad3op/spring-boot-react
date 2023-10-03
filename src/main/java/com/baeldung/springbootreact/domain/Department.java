package com.baeldung.springbootreact.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("departments")
public class Department {
    @Id
    private Long id;
    private String name;

    public static Mono<Department> fromRows(List<Map<String, Object>> rows) {
        return Mono.just(Department.builder()
                .id((Long.parseLong(rows.get(0).get("d_id").toString())))
                .name((String) rows.get(0).get("d_name"))
                .build());
    }

}
