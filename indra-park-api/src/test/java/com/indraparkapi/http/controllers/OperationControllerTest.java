package com.indraparkapi.http.controllers;

import com.indraparkapi.BaseTest;
import com.indraparkapi.persistence.models.Operation;
import com.indraparkapi.persistence.models.Vehicle;
import com.indraparkapi.persistence.repositories.OperationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional()
public class OperationControllerTest extends BaseTest {
    private static final String BASE_URL = "/api/operations";

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationController operationController;

    @Autowired
    private MockMvc mockMvc;

    private Operation operation;
    private Vehicle car;
    private String from;
    private String to;

    @Before
    public void setUp() {
        car = new Vehicle(Vehicle.VehicleType.Car, "Ferrari 488 Pista", "JAV0000");
        operation = new Operation(car, Operation.OperationType.IN, this.now(), null);

        from = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.now().minusDays(1));
        to = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.now().plusDays(1));
    }

    @Test
    public void it_should_load_context() {
        assertThat(operationController).isNotNull();
    }

    @Test
    public void it_should_list_operations_without_parameters() throws Exception {
        operationRepository.save(operation);

        mockMvc.perform(get(BASE_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(operation.getId().intValue())));
    }

    @Test
    public void it_should_list_operations_between_dates() throws Exception {
        operationRepository.save(operation);

        mockMvc.perform(get(BASE_URL).param("from", from).param("to", to)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(operation.getId().intValue())));
    }

    @Test
    public void it_should_return_a_empty_list_operations_between_dates() throws Exception {
        operationRepository.save(operation);

        mockMvc.perform(get(BASE_URL).param("from", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.now().plusDays(1))).param("to", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.now().plusDays(1)))).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void it_should_list_operations_by_plate() throws Exception {
        operationRepository.save(operation);
        mockMvc.perform(get(BASE_URL).param("plate", operation.getVehicle().getPlate())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]['vehicle']['plate']", is(operation.getVehicle().getPlate())));
    }

    @Test
    public void it_should_list_operations_between_dates_and_plate() throws Exception {
        operationRepository.save(operation);
        mockMvc.perform(get(BASE_URL).param("from", from).param("to", to).param("plate", operation.getVehicle().getPlate())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]['vehicle']['plate']", is(operation.getVehicle().getPlate())));
    }


    @Test
    public void it_should_make_a_vehicle_entry() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(car)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$['vehicle']['plate']", is(operation.getVehicle().getPlate())));
    }

    @Test
    public void it_should_make_a_vehicle_exit() throws Exception {
        operationRepository.save(operation);
        mockMvc.perform(get(BASE_URL + "/" + operation.getId() + "/exit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$['vehicle']['plate']", is(operation.getVehicle().getPlate())))
                .andExpect(jsonPath("$['exitedAt']", containsString(DateTimeFormatter.ofPattern("y-MM-dd").format(this.now()))));
    }

    @Test
    public void it_should_make_a_vehicle_calculation() throws Exception {
        operationRepository.save(operation);
        mockMvc.perform(get(BASE_URL + "/" + operation.getId() + "/calculate"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void it_should_get_report() throws Exception {
        operationRepository.save(operation);
        mockMvc.perform(get(BASE_URL + "/report"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}