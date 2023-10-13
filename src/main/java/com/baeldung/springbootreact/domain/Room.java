package com.baeldung.springbootreact.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private Long parent_id;
    private Integer num;

    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    @Builder.Default
    private List<Room> children = new ArrayList<>();

    public static Mono<Room> fromRows(List<Map<String, Object>> rows) {
        Object num = rows.get(0).get("r_num");
        return Mono.just(Room.builder()
                .id((Long.parseLong(rows.get(0).get("r_id").toString())))
                .roomType(Objects.nonNull(rows.get(0).get("room_type")) ? RoomType.to11(rows.get(0).get("room_type").toString()) : null)
                .num((Integer.parseInt((Objects.nonNull(num) ? num : 0).toString())))
                .reservations(rows.stream()
                        .map(Reservation::fromRow)
                        .filter(Objects::nonNull)
                        .toList())
                .build());
    }

//    public static Mono<Room> fromRowSingle(Map<String, Object> row) {
//        Object num = rows.get(0).get("r_num");
//        return Mono.just(Room.builder()
//                .id((Long.parseLong(rows.get(0).get("r_id").toString())))
//                .roomType(Objects.nonNull(rows.get(0).get("room_type"))?  RoomType.to11(rows.get(0).get("room_type").toString()):null)
//                .num((Integer.parseInt((Objects.nonNull(num)?num:0).toString())))
//                .reservations(rows.stream()
//                        .map(Reservation::fromRow)
//                        .filter(Objects::nonNull)
//                        .toList())
//                .build());
//    }

    public static Room fromRowSingle(Map<String, Object> row) {
        if (row.get("res_id") != null) {
            return Room.builder()
                    .build();
        } else {
            return null;
        }
    }


    public static Mono<Room> fromRowsAll(List<Map<String, Object>> rows) {
        Object num = rows.get(0).get("f_num");
        return Mono.just(Room.builder()
                .id((Long.parseLong(rows.get(0).get("f_id").toString())))
                .roomType(Objects.nonNull(rows.get(0).get("f_room_type")) ? RoomType.to11(rows.get(0).get("f_room_type").toString()) : null)
                .num((Integer.parseInt((Objects.nonNull(num) ? num : 0).toString())))
                .children(rows.stream()
                        .map(Room::fromRowSingle)
                        .filter(Objects::nonNull)
                        .toList()
                )
                .build());
    }
}
