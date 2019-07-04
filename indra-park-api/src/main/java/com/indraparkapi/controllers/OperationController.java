package com.indraparkapi.controllers;

import com.indraparkapi.models.Operation;
import com.indraparkapi.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping()
    public ResponseEntity<List<Operation>> all(
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromData,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toData,
            @RequestParam(name = "plate", required = false) String plate
    ) {
        return ResponseEntity.ok(this.operationService.filter(fromData, toData, plate == null || plate.equals("") ? null : plate));
    }
}
