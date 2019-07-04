package com.indraparkapi.repositories;

import com.indraparkapi.models.Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository()
public interface OperationRepository extends CrudRepository<Operation, Long> {

    @Query(
            value = "select op from Operation op where ((cast(:fromDateTime as timestamp) IS NULL or cast(:toDateTime as timestamp) IS NULL) or (op.enteredAt between :fromDateTime and :toDateTime)) and (:plate IS NULL or op.vehicle.plate LIKE %:plate%)"
    )
    List<Operation> filter(
            @Param("fromDateTime") LocalDateTime from,
            @Param("toDateTime") LocalDateTime to,
            @Param("plate") String plate
    );
}