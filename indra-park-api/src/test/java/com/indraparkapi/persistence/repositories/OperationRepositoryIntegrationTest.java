package com.indraparkapi.persistence.repositories;

import com.indraparkapi.BaseTest;
import com.indraparkapi.persistence.models.Operation;
import com.indraparkapi.persistence.models.Vehicle;
import com.indraparkapi.persistence.specifications.OperationSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional()
public class OperationRepositoryIntegrationTest extends BaseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OperationRepository operationRepository;
    private Operation operation;
    private Vehicle car;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        car = new Vehicle(Vehicle.VehicleType.Car, "Ferrari 488 Pista", "JAV0000");
        operation = new Operation(car, Operation.OperationType.IN, this.now(), null);
    }

    @Test()
    public void it_should_save_a_operation() {
        Operation saved = operationRepository.save(operation);
        assertThat(saved).isNotNull().hasSameClassAs(operation);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getExitedAt()).isEqualTo(operation.getExitedAt());
        assertThat(saved.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(saved.getType()).isEqualTo(operation.getType());
        assertThat(saved.getVehicle()).isNotNull();
        assertThat(saved.getVehicle().getType()).isEqualTo(operation.getVehicle().getType());
        assertThat(saved.getVehicle().getPlate()).isEqualTo(operation.getVehicle().getPlate());
        assertThat(saved.getVehicle().getModel()).isEqualTo(operation.getVehicle().getModel());
    }

    @Test()
    public void it_should_filter_operations_between_datetime() {
        entityManager.persistAndFlush(operation);

        List<Operation> result = operationRepository.findAll(
                OperationSpecification.filter(
                        operation.getEnteredAt().minusHours(24),
                        operation.getEnteredAt(),
                        null
                )
        );

        assertThat(result.size()).isEqualTo(1);
        Operation saved = result.get(0);

        assertThat(saved).isNotNull().hasSameClassAs(operation);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getExitedAt()).isEqualTo(operation.getExitedAt());
        assertThat(saved.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(saved.getType()).isEqualTo(operation.getType());
        assertThat(saved.getVehicle()).isNotNull();
        assertThat(saved.getVehicle().getType()).isEqualTo(operation.getVehicle().getType());
        assertThat(saved.getVehicle().getPlate()).isEqualTo(operation.getVehicle().getPlate());
        assertThat(saved.getVehicle().getModel()).isEqualTo(operation.getVehicle().getModel());
    }

    @Test()
    public void it_should_filter_operations_between_datetime_and_return_empty_list() {
        operation.setEnteredAt(this.now().minusDays(2));
        entityManager.persist(operation);

        List<Operation> result = operationRepository.findAll(
                OperationSpecification.filter(
                        operation.getEnteredAt().minusDays(1),
                        operation.getEnteredAt(),
                        null
                )
        );

        assertThat(result.size()).isEqualTo(1);
        Operation saved = result.get(0);

        assertThat(saved).isNotNull().hasSameClassAs(operation);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getExitedAt()).isEqualTo(operation.getExitedAt());
        assertThat(saved.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(saved.getType()).isEqualTo(operation.getType());
        assertThat(saved.getVehicle()).isNotNull();
        assertThat(saved.getVehicle().getType()).isEqualTo(operation.getVehicle().getType());
        assertThat(saved.getVehicle().getPlate()).isEqualTo(operation.getVehicle().getPlate());
        assertThat(saved.getVehicle().getModel()).isEqualTo(operation.getVehicle().getModel());
    }

    @Test()
    public void it_should_filter_operations_by_plate() {
        entityManager.persistAndFlush(operation);
        List<Operation> result = operationRepository.findAll(
                OperationSpecification.filter(
                        null, null, operation.getVehicle().getPlate()
                )
        );
        assertThat(result.size()).isEqualTo(1);
        Operation saved = result.get(0);

        assertThat(saved).isNotNull().hasSameClassAs(operation);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getExitedAt()).isEqualTo(operation.getExitedAt());
        assertThat(saved.getEnteredAt()).isEqualTo(operation.getEnteredAt());
        assertThat(saved.getType()).isEqualTo(operation.getType());
        assertThat(saved.getVehicle()).isNotNull();
        assertThat(saved.getVehicle().getType()).isEqualTo(operation.getVehicle().getType());
        assertThat(saved.getVehicle().getPlate()).isEqualTo(operation.getVehicle().getPlate());
        assertThat(saved.getVehicle().getModel()).isEqualTo(operation.getVehicle().getModel());
    }

    @Test()
    public void it_should_get_last_seven_days() {

    }
}