package com.indraparkapi.models;

import java.util.Date;

public class OperationValueResult {

    public static final int SECOND_TIMESTAMP = 1000;
    public static final int MINUTE_TIMESTAMP = OperationValueResult.SECOND_TIMESTAMP * 60;
    public static final int HOUR_TIMESTAMP = OperationValueResult.MINUTE_TIMESTAMP * 60;

    public static final double MIN_PERMANENCY_HOURS = 1.0;

    private double hours;
    private double minutes;
    private double seconds;
    private double value;

    private double totalHours;

    private Long operationId;
    private Date enteredAt;
    private Date exitedAt;

    private Vehicle.VehicleStrategy vehicleStrategy;

    public static OperationValueResult calculateFor(Operation operation, Date exitedAt) throws Exception {
        if (operation.getEnteredAt() == null) {
            throw new Exception("Operation Entered at shouldn't be null.");
        }
        return (new OperationValueResult(operation.getId(), operation.getVehicle().getType(), operation.getEnteredAt(), exitedAt));
    }

    protected OperationValueResult(Long operationId, Vehicle.VehicleStrategy vehicleStrategy, Date enteredAt, Date exitedAt) {
        this.operationId = operationId;
        this.enteredAt = enteredAt;
        this.exitedAt = exitedAt;
        this.vehicleStrategy = vehicleStrategy;
        this.calculate();
    }

    public void calculate() {
        long exitedAtTime = exitedAt.getTime();
        long enteredAtTime = enteredAt.getTime();

        double diff = (double) exitedAtTime - (double) enteredAtTime;
        double totalHours = diff / HOUR_TIMESTAMP;
        this.hours = Math.floor(totalHours);

        double totalMinutes = (totalHours - this.hours) * 60;
        this.minutes = Math.floor(totalMinutes);

        double totalSeconds = (totalMinutes - this.minutes) * 60;
        this.seconds = Math.floor(totalSeconds);

        this.totalHours = Math.max(Math.ceil(totalHours), MIN_PERMANENCY_HOURS);

        this.value = this.totalHours * vehicleStrategy.getHourPrice();
    }

    public Long getOperationId() {
        return operationId;
    }

    public Date getEnteredAt() {
        return enteredAt;
    }

    public Date getExitedAt() {
        return exitedAt;
    }

    public double getValue() {
        return value;
    }

    public double getHours() {
        return hours;
    }

    public double getMinutes() {
        return minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    public double getTotalHours() {
        return totalHours;
    }
}
