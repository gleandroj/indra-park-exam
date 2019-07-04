package com.indraparkapi.persistence.repositories;

import com.indraparkapi.persistence.models.Operation;
import com.indraparkapi.persistence.models.LastDaysDataSet;
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
                    "select last_days.entered_at as enteredAt, " +
                    "       json_agg(json_build_object('type', last_days.vehicle_type, 'quantity', last_days.quantity)) as types " +
                    "from last_days " +
                    "group by last_days.entered_at",
            nativeQuery = true
    )
    List<LastDaysDataSet> findCountPerDay(
            @Param("fromDate") LocalDateTime from,
            @Param("toDate") LocalDateTime to
    );
}