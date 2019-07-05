package com.indraparkapi.http.controllers;

import com.indraparkapi.exceptions.ApiException;
import com.indraparkapi.persistence.models.Operation;
import com.indraparkapi.persistence.models.OperationValueResult;
import com.indraparkapi.persistence.models.Vehicle;
import com.indraparkapi.persistence.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> filter(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromData,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toData,
            @RequestParam(name = "plate", required = false) String plate,
            @PageableDefault(size = 10, sort = "enteredAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(this.operationService.filter(
                fromData, toData, plate, pageable
        ));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Operation> entry(
            @RequestBody Vehicle vehicle
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operationService.entry(vehicle));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/exit")
    public ResponseEntity<Operation> exit(
            @PathVariable("id") long operationId
    ) throws ApiException {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.exit(
                operationService.findOrFail(operationId)
        ));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/calculate")
    public ResponseEntity<OperationValueResult> calculate(
            @PathVariable("id") long operationId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.calculate(
                operationService.findOrFail(operationId)
        ));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/report")
    public ResponseEntity<?> report() {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.countVehicleTypeLastSevenDays());
    }
}
