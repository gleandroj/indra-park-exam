package com.indraparkapi.models;

import javax.persistence.*;

@Embeddable
public class Vehicle {

    public Vehicle(VehicleType type, String model, String plate) {
        this.setType(type);
        this.setModel(model);
        this.setPlate(plate);
    }

    public VehicleType getType() {
        return type;
    }

    public interface VehicleStrategy {
        double getHourPrice();
    }

    public enum VehicleType implements VehicleStrategy {
        Car {
            public double getHourPrice() {
                return 15.00;
            }
        },
        Motorcycle {
            public double getHourPrice() {
                return 10.00;
            }
        },
        Truck {
            public double getHourPrice() {
                return 35.00;
            }
        },
        Pickup {
            public double getHourPrice() {
                return 20.00;
            }
        }
    }

    @Column(name = "vehicle_type")
    private VehicleType type;

    @Column(name = "vehicle_model")
    private String model;

    @Column(name = "vehicle_plate")
    private String plate;

    public Vehicle() {
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}