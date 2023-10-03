package com.baeldung.springbootreact.repository;

import com.baeldung.springbootreact.domain.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentRepository {
    private final DatabaseClient client;
//    private static final String SELECT_QUERY = """
//            SELECT d.id d_id, d.name d_name, m.id m_id, m.first_name m_firstName, m.last_name m_lastName,
//                m.position m_position, m.is_full_time m_isFullTime, e.id e_id, e.first_name e_firstName,
//                e.last_name e_lastName, e.position e_position, e.is_full_time e_isFullTime
//            FROM departments d
//            LEFT JOIN department_managers dm ON dm.department_id = d.id
//            LEFT JOIN employees m ON m.id = dm.employee_id
//            LEFT JOIN department_employees de ON de.department_id = d.id
//            LEFT JOIN employees e ON e.id = de.employee_id
//            """;

    private static final String SELECT_QUERY = """
            SELECT d.id d_id, d.name d_name
            FROM departments d
            """;

    public Flux<Department> findAll() {
        String query = String.format("%s ORDER BY d.id", SELECT_QUERY);

        return client.sql(query)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("d_id"))
                .flatMap(Department::fromRows);
    }


    public Mono<Department> findById(long id) {
        String query = String.format("%s WHERE d.id = :id", SELECT_QUERY);

        return client.sql(query)
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("d_id"))
                .flatMap(Department::fromRows)
                .singleOrEmpty();
    }

    public Mono<Department> findByName(String name) {
        String query = String.format("%s WHERE d.name = :name", SELECT_QUERY);

        return client.sql(query)
                .bind("name", name)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("d_id"))
                .flatMap(Department::fromRows)
                .singleOrEmpty();
    }

    @Transactional
    public Mono<Department> save(Department department) {
        return this.saveDepartment(department);
    }



    private Mono<Department> saveDepartment(Department department) {
        if (department.getId() == null) {
            return client.sql("INSERT INTO departments(name) VALUES(:name)")
                    .bind("name", department.getName())
                    .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                    .fetch().first()
                    .doOnNext(result -> department.setId(Long.parseLong(result.get("id").toString())))
                    .thenReturn(department);
        } else {
            return this.client.sql("UPDATE departments SET name = :name WHERE id = :id")
                    .bind("name", department.getName())
                    .bind("id", department.getId())
                    .fetch().first()
                    .thenReturn(department);
        }
    }

    private Mono<Void> deleteDepartment(Department department) {
        return client.sql("DELETE FROM departments WHERE id = :id")
                .bind("id", department.getId())
                .fetch().first()
                .then();
    }

}
