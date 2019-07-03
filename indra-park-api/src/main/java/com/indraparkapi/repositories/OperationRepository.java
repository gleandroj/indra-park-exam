package com.indraparkapi.repositories;

import com.indraparkapi.models.Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Long> {

    @Query(
            value = "select * from operation op where (op.entered_at between :from and :to) and (:plate is null or op.vehicle_plate = :plate)",
            nativeQuery = true
    )
    List<Operation> findBetweenAndOptionalVehiclePlate(
            @Param("from") Date from,
            @Param("to") Date to,
            @Param("plate") String plate
    );

}