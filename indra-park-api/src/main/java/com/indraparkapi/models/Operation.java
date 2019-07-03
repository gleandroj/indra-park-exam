package com.indraparkapi.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Operation {

    public enum OperationType {
        IN,
        OUT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Embedded()
    private Vehicle vehicle;

    @Column(name = "operation_type")
    private OperationType type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date exitedAt;

    public Operation(Vehicle vehicle, OperationType type, Date enteredAt) {
        this.setVehicle(vehicle);
        this.setType(type);
        this.setEnteredAt(enteredAt);
    }

    public Operation(Vehicle vehicle, OperationType type, Date enteredAt, Date exitedAt) {
        this(vehicle, type, enteredAt);
        this.setExitedAt(exitedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = (long) id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Date getEnteredAt() {
        return enteredAt;
    }

    public void setEnteredAt(Date enteredAt) {
        this.enteredAt = enteredAt;
    }

    public Date getExitedAt() {
        return exitedAt;
    }

    public void setExitedAt(Date exitedAt) {
        this.exitedAt = exitedAt;
    }
}