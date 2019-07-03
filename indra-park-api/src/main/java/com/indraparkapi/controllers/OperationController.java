package com.indraparkapi.controllers;

import com.indraparkapi.repositories.OperationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/operations")
public class OperationController {

    private OperationRepository operationRepository;

    public OperationController(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(this.operationRepository.findAll());
    }
}
