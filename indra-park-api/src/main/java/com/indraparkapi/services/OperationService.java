package com.indraparkapi.services;

import com.indraparkapi.models.Operation;
import com.indraparkapi.models.OperationValueResult;
import com.indraparkapi.models.Vehicle;
import com.indraparkapi.repositories.OperationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service()
public class OperationService {

    private OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    public List<Operation> filter(
            LocalDateTime from,
            LocalDateTime to,
            String plate
    ) {
        return this.operationRepository.filter(
                from, to, plate
        );
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
}
