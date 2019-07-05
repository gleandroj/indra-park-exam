package com.indraparkapi.persistence.models;

import com.indraparkapi.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OperationValueResultTest extends BaseTest {

    private Operation operation;
    private Vehicle car;
    private Vehicle motorCycle;
    private Vehicle truck;
    private Vehicle pickup;

    @Before
    public void setUp() {
        car = new Vehicle(Vehicle.VehicleType.Car, "Ferrari 488 Pista", "JAV0000");
        motorCycle = new Vehicle(Vehicle.VehicleType.Motorcycle, "Ducati 1199 Panigale S", "JAV0001");
        truck = new Vehicle(Vehicle.VehicleType.Truck, "Axor 4144 6x4", "JAV0003");
        pickup = new Vehicle(Vehicle.VehicleType.Pickup, "Range Rover Sport SVR Facelift", "JAV0004");
        operation = new Operation(car, Operation.OperationType.IN, this.now(), null);
        operation.setId(1);
    }

    @Test()
    public void it_should_allow_to_calculate_computed_price() throws Exception {
        OperationValueResult computed = OperationValueResult.calculateFor(operation, this.now());

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat(computed.getTotalHours()).isNotNull().isNotNaN();
        assertThat(computed.getValue()).isNotNull().isNotNaN();
        assertThat(computed.getMinutes()).isNotNull().isNotNaN();
        assertThat(computed.getSeconds()).isNotNull().isNotNaN();
        assertThat(computed.getHours()).isNotNull().isNotNaN();
    }

    @Test()
    public void it_should_return_30_when_a_car_stay_only_1_hour_and_30_minutes() throws Exception {
        LocalDateTime date = this.now().minusHours(1).minusMinutes(30);

        operation.setEnteredAt(date);
        operation.setVehicle(car);

        OperationValueResult computed = OperationValueResult.calculateFor(operation, this.now());

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat((int) computed.getTotalHours()).isEqualTo(2);
        assertThat((int) computed.getHours()).isEqualTo(1);
        assertThat((int) computed.getMinutes()).isEqualTo(30);
        assertThat(computed.getValue()).isEqualTo(30);
    }

    @Test()
    public void it_should_return_15_when_a_car_stay_0_hour() throws Exception {
        operation.setEnteredAt(this.now());
        operation.setVehicle(car);
        OperationValueResult computed = OperationValueResult.calculateFor(operation, this.now());

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat((int) computed.getTotalHours()).isEqualTo(1);
        assertThat((int) computed.getHours()).isEqualTo(0);
        assertThat(computed.getValue()).isEqualTo(15);
    }

    @Test()
    public void it_should_return_30_when_a_car_stay_2_hour() throws Exception {
        operation.setEnteredAt(this.now().minusHours(2));
        operation.setVehicle(car);
        OperationValueResult computed = OperationValueResult.calculateFor(operation, this.now());

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat((int) computed.getTotalHours()).isEqualTo(2);
        assertThat(computed.getValue()).isEqualTo(30);
    }

    @Test()
    public void it_should_return_20_when_a_motorcycle_stay_2_hour() throws Exception {
        operation.setEnteredAt(this.now().minusHours(2));
        operation.setVehicle(motorCycle);
        OperationValueResult computed = OperationValueResult.calculateFor(operation, this.now());

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat((int) computed.getTotalHours()).isEqualTo(2);
        assertThat(computed.getValue()).isEqualTo(20);
    }

    @Test()
    public void it_should_return_70_when_a_truck_stay_2_hour() throws Exception {
        operation.setEnteredAt(this.now().minusHours(2));
        operation.setVehicle(truck);
        OperationValueResult computed = OperationValueResult.calculateFor(operation, this.now());

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat((int) computed.getTotalHours()).isEqualTo(2);
        assertThat(computed.getValue()).isEqualTo(70);
    }

    @Test()
    public void it_should_return_40_when_a_pickup_stay_2_hour() throws Exception {
        operation.setEnteredAt(this.now().minusHours(2));
        operation.setVehicle(pickup);
        OperationValueResult computed = OperationValueResult.calculateFor(operation, this.now());

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat((int) computed.getTotalHours()).isEqualTo(2);
        assertThat((int) computed.getHours()).isEqualTo(2);
        assertThat(computed.getValue()).isEqualTo(40);
    }


    @Test(expected = Exception.class)
    public void it_should_throw_a_exception_when_entered_at_is_null() throws Exception {
        operation.setEnteredAt(null);
        OperationValueResult.calculateFor(operation, this.now());
    }

}