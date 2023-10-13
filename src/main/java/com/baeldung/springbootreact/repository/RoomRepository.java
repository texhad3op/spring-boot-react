package com.baeldung.springbootreact.repository;

import com.baeldung.springbootreact.domain.Department;
import com.baeldung.springbootreact.domain.Room;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RoomRepository {

    private final DatabaseClient client;

    String sql = """
            WITH RECURSIVE a AS (
            SELECT id, parent_id, 1::integer recursion_level, room_type, num
            FROM rooms
            WHERE parent_id IS NULL
            UNION ALL
            SELECT r.id, r.parent_id, a.recursion_level +1 , r.room_type, r.num
            FROM rooms r
            JOIN a ON a.id = r.parent_id
            ) SELECT a.id, a.parent_id, a.room_type, a.num FROM a
            where a.room_type != 'building'
            """;

    public Flux<Room> findAll() {
        //String query = String.format("%s ORDER BY d.id", SELECT_QUERY);

        return client.sql(sql)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("id"))
                //.buffer()
                .flatMap(Room::fromRows);
    }


    private static final String SELECT_QUERY = """
            SELECT r.id r_id, r.num r_num, r.room_type room_type,res.id res_id, res.start start_e, res.finish finish_e
            FROM rooms r
            LEFT JOIN reservations res ON r.id = res.room_id
            where r.room_type = 'room'
            order by r_id
            """;

    public Flux<Room> retrieveAllReservations(){
        return client.sql(SELECT_QUERY2)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("r_id"))
                .flatMap(Room::fromRows);
    }



    private static final String SELECT_QUERY2 = """
        select
        f.id f_id, f.num f_num, f.room_type f_room_type,
        r.id r_id, r.num r_num, r.room_type r_room_type,
        res.id res_id, res.start start_e, res.finish finish_e
        from rooms f
        left join rooms r on f.id = r.parent_id
        left join reservations res on r.id = res.room_id
        where f.room_type = 'floor' and r.room_type = 'room'
        order by f.num, r.num
            """;

    public Flux<Room> retrieveAllReservationsData(){
        return client.sql(SELECT_QUERY2)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("f_id"))
                .flatMap(Room::fromRowsAll);
    }


    public Flux<Room> retrieveAllReservationsee(){
        return client.sql(SELECT_QUERY2)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("f_id"), (a, b) -> {
                    System.out.println();
                    return true;
                })
                .flatMap(Room::fromRowsAll);
    }

}
