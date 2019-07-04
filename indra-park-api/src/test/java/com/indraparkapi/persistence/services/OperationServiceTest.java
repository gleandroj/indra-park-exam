package com.indraparkapi.persistence.services;

import com.indraparkapi.BaseTest;
import com.indraparkapi.persistence.models.Operation;
import com.indraparkapi.persistence.models.OperationValueResult;
import com.indraparkapi.persistence.models.Vehicle;
import com.indraparkapi.persistence.repositories.OperationRepository;
import com.indraparkapi.persistence.specifications.OperationSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest extends BaseTest {

    @InjectMocks
    private OperationService operationService;

    @Mock
    private OperationRepository operationRepository;
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
    public void it_should_make_a_entry_operation() {
        Mockito.when(operationRepository.save(any(Operation.class)))
                .thenReturn(operation);

        Operation entry = operationService.entry(car);

        assertThat(entry).isNotNull();
        assertThat(entry.getId()).isNotNull();
        assertThat(entry.getId()).isGreaterThan(0);
        assertThat(entry.getType()).isEqualTo(operation.getType()).isEqualTo(Operation.OperationType.IN);
        assertThat(entry.getEnteredAt()).isNotNull();
        assertThat(entry.getExitedAt()).isNull();
        assertThat(entry.getVehicle()).isNotNull();
        assertThat(entry.getVehicle().getModel()).isEqualTo(car.getModel());
        assertThat(entry.getVehicle().getPlate()).isEqualTo(car.getPlate());
        assertThat(entry.getVehicle().getType()).isEqualTo(car.getType());
    }

    @Test()
    public void it_should_allow_to_calculate_computed_price() throws Exception {
        OperationValueResult computed = operationService.calculate(operation);

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat(computed.getTotalHours()).isNotNull().isNotNaN();
        assertThat(computed.getValue()).isNotNull().isNotNaN();
    }

    @Test()
    public void it_should_return_30_when_a_car_stay_only_1_hour_and_30_minutes() throws Exception {
        LocalDateTime date = this.now().minusHours(1).minusMinutes(30);

        operation.setEnteredAt(date);
        operation.setVehicle(car);

        OperationValueResult computed = operationService.calculate(operation);

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

        OperationValueResult computed = operationService.calculate(operation);

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

        OperationValueResult computed = operationService.calculate(operation);

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

        OperationValueResult computed = operationService.calculate(operation);

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

        OperationValueResult computed = operationService.calculate(operation);

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

        OperationValueResult computed = operationService.calculate(operation);

        assertThat(computed).isNotNull();
        assertThat(computed.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(computed.getExitedAt()).isNotNull();
        assertThat(computed.getOperationId()).isEqualTo(operation.getId());
        assertThat((int) computed.getTotalHours()).isEqualTo(2);
        assertThat((int) computed.getHours()).isEqualTo(2);
        assertThat(computed.getValue()).isEqualTo(40);
    }

    @Test()
    public void it_should_make_a_exit_operation() {
        operation.setVehicle(car);

        Mockito.when(operationRepository.save(any(Operation.class)))
                .then(returnsFirstArg());

        Operation exit = operationService.exit(operation);

        assertThat(exit).isNotNull();
        assertThat(exit.getId()).isNotNull();
        assertThat(exit.getId()).isGreaterThan(0);
        assertThat(exit.getType()).isEqualTo(operation.getType()).isEqualTo(Operation.OperationType.OUT);
        assertThat(exit.getEnteredAt()).isNotNull();
        assertThat(exit.getExitedAt()).isNotNull();
        assertThat(exit.getVehicle()).isNotNull();
        assertThat(exit.getVehicle().getModel()).isEqualTo(car.getModel());
        assertThat(exit.getVehicle().getPlate()).isEqualTo(car.getPlate());
        assertThat(exit.getVehicle().getType()).isEqualTo(car.getType());
    }

    @Test()
    public void it_should_return_a_list_of_operations() {
        List<Operation> operations = new ArrayList<>();
        operations.add(operation);

        Mockito.when(operationRepository.findAll(any())).thenReturn(operations);

        List<Operation> list = operationService.filter(null, null, null);

        assertThat(list.contains(operation)).isTrue();
        assertThat(list.size()).isEqualTo(1);
    }
}