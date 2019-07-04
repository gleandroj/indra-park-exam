package com.indraparkapi.controllers;

import com.indraparkapi.exceptions.ApiException;
import com.indraparkapi.models.Operation;
import com.indraparkapi.models.OperationValueResult;
import com.indraparkapi.models.Vehicle;
import com.indraparkapi.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Operation>> all(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromData,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toData,
            @RequestParam(name = "plate", required = false) String plate
    ) {
        return ResponseEntity.ok(this.operationService.filter(fromData, toData, plate == null || plate.equals("") ? null : plate));
    }

    //TODO: Test
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Operation> entry(
            @RequestBody Vehicle vehicle
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operationService.entry(vehicle));
    }

    //TODO: Test
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/exit")
    public ResponseEntity<Operation> exit(
            @PathVariable("id") long operationId
    ) throws ApiException {
        return ResponseEntity.status(HttpStatus.CREATED).body(operationService.exit(
                operationService.findOrFail(operationId)
        ));
    }

    //TODO: Test
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/calculate")
    public ResponseEntity<OperationValueResult> calculate(
            @PathVariable("id") long operationId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(operationService.calculate(
                operationService.findOrFail(operationId)
        ));
    }

    //TODO: Dashboard Response
}
