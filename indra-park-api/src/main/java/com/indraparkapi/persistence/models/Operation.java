package com.indraparkapi.persistence.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "operations")
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Operation extends BaseModel {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    public enum OperationType {
        IN,
        OUT
    }

    @Embedded()
    private Vehicle vehicle;

    @Column(name = "operation_type")
    private OperationType type;

    @Column(name = "entered_at")
    private LocalDateTime enteredAt;

    @Column(name = "exited_at")
    private LocalDateTime exitedAt;

    public Operation() {
    }

    public Operation(Vehicle vehicle, OperationType type, LocalDateTime enteredAt) {
        this.setVehicle(vehicle);
        this.setType(type);
        this.setEnteredAt(enteredAt);
    }

    public Operation(Vehicle vehicle, OperationType type, LocalDateTime enteredAt, LocalDateTime exitedAt) {
        this(vehicle, type, enteredAt);
        this.setExitedAt(exitedAt);
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

    public LocalDateTime getEnteredAt() {
        return enteredAt;
    }

    public void setEnteredAt(LocalDateTime enteredAt) {
        this.enteredAt = enteredAt;
    }

    public LocalDateTime getExitedAt() {
        return exitedAt;
    }

    public void setExitedAt(LocalDateTime exitedAt) {
        this.exitedAt = exitedAt;
    }
}