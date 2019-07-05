package com.indraparkapi.persistence.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.indraparkapi.exceptions.ApiException;
import com.indraparkapi.persistence.models.Operation;
import com.indraparkapi.persistence.models.OperationValueResult;
import com.indraparkapi.persistence.models.Vehicle;
import com.indraparkapi.persistence.repositories.OperationRepository;
import com.indraparkapi.persistence.specifications.OperationSpecification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    private OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Operation findOrFail(long id) throws ApiException {
        Optional<Operation> op = this.operationRepository.findById(
                id
        );
        if (!op.isPresent()) {
            throw ApiException.modelNotFound();
        }
        return op.get();
    }

    public List<Operation> filter(
            LocalDateTime from,
            LocalDateTime to,
            String plate
    ) {
        return this.operationRepository.findAll(OperationSpecification.filter(from, to, plate));
    }

    public Operation entry(Vehicle vehicle) {
        return this.operationRepository.save(new Operation(vehicle, Operation.OperationType.IN, LocalDateTime.now()));
    }

    public Operation exit(Operation operation) {
        operation.setExitedAt(LocalDateTime.now());
        operation.setType(Operation.OperationType.OUT);
        return this.operationRepository.save(operation);
    }

    public OperationValueResult calculate(Operation operation) throws Exception {
        return OperationValueResult.calculateFor(operation, LocalDateTime.now());
    }

    public List<ObjectNode> countVehicleTypeLastSevenDays() {
        LocalDateTime from = LocalDateTime.of(LocalDate.now().minusDays(6), LocalTime.of(0, 0, 0));
        LocalDateTime to = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        return this.operationRepository.countVehicleTypeBetween(from, to);
    }
}
