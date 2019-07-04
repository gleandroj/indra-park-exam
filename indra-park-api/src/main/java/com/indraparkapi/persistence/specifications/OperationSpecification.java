package com.indraparkapi.persistence.specifications;

import com.indraparkapi.persistence.models.Operation;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OperationSpecification {

    private static Specification<Operation> vehiclePlateContains(String plate) {
        return (op, cq, cb) -> plate != null ? cb.like(cb.lower(op.get("vehicle").get("plate")), "%" + plate.toLowerCase() + "%") : null;
    }

    private static Specification<Operation> enteredAtBetween(LocalDateTime from, LocalDateTime to) {
        return (op, cq, cb) -> from != null && to != null ? cb.between(op.get("enteredAt"), from, to) : null;
    }

    public static Specification<Operation> filter(LocalDateTime from, LocalDateTime to, String plate) {
        return Specification.where(enteredAtBetween(from, to)).and(vehiclePlateContains(plate));
    }
}
