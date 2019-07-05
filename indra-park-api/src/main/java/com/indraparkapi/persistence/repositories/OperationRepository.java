package com.indraparkapi.persistence.repositories;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.indraparkapi.persistence.models.Operation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository()
public interface OperationRepository extends CrudRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    @Query(
            value = "with last_days as ( " +
                    "    select series.day  as entered_at , " +
                    "           series.type as vehicle_type, " +
                    "           count(op)   as quantity " +
                    "    from operations op " +
                    "             right join (select s.day, vts.type " +
                    "                         from generate_series(cast(:fromDate as timestamp), cast(:toDate as timestamp), interval '1 day') s(day) " +
                    "                         cross join generate_series(0, 3) vts(type)) series(day, type) " +
                    "                         on series.day = date_trunc('day', op.entered_at) and series.type = op.vehicle_type " +
                    "                         group by series.day, series.type) " +
                    "select json_build_object('vehicleType', last_days.vehicle_type, 'data', jsonb_agg(json_build_array(extract(epoch from last_days.entered_at) * 1000, last_days.quantity)))" +
                    "from last_days " +
                    "group by last_days.vehicle_type order by last_days.vehicle_type ASC",
            nativeQuery = true
    )
    List<ObjectNode> countVehicleTypeBetween(
            @Param("fromDate") LocalDateTime from,
            @Param("toDate") LocalDateTime to
    );
}