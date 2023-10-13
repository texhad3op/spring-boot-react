package com.baeldung.springbootreact.domain;


import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation {
    @Id
    private Long id;
    private LocalDateTime start;
    private LocalDateTime finish;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    //LocalDateTime dateTime = ;
    public static Reservation fromRow(Map<String, Object> row) {
        if (row.get("res_id") != null) {
            return Reservation.builder()
                    .id((Long.parseLong(row.get("res_id").toString())))
                    .start(LocalDateTime.parse(String.valueOf(row.get("start_e")), formatter))
                    .finish(LocalDateTime.parse(String.valueOf(row.get("finish_e")), formatter))
                    .build();
        } else {
            return null;
        }
    }

}
