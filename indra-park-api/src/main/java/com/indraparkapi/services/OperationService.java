package com.indraparkapi.services;

import com.indraparkapi.models.Operation;
import com.indraparkapi.models.OperationValueResult;
import com.indraparkapi.models.Vehicle;
import com.indraparkapi.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    public List<Operation> list(
            Date from,
            Date to,
            String plate
    ) {
        return this.operationRepository.findBetweenAndOptionalVehiclePlate(
                from, to, plate
        );
    }

    public Operation entry(Vehicle vehicle) {
        return this.operationRepository.save(new Operation(vehicle, Operation.OperationType.IN, new Date()));
    }

    public Operation exit(Operation operation) {
        operation.setExitedAt(new Date());
        operation.setType(Operation.OperationType.OUT);
        return this.operationRepository.save(operation);
    }

    public OperationValueResult calculate(Operation operation) throws Exception {
        return OperationValueResult.calculateFor(operation, new Date());
    }
}
