package com.SoftUni.DriverServiceProject.Web;

import com.SoftUni.DriverServiceProject.Models.DTO.CarAddBindingModel;
import com.SoftUni.DriverServiceProject.Models.DTO.DriverAddBindingModel;
import com.SoftUni.DriverServiceProject.Models.DTO.GarageAddBindingModel;
import com.SoftUni.DriverServiceProject.Models.Entity.User;
import com.SoftUni.DriverServiceProject.Models.Enums.CarTypeEnum;
import com.SoftUni.DriverServiceProject.Repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser("nek@test.com")
@SpringBootTest
@AutoConfigureMockMvc
public class AdminRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;



    private User testUser;


    @BeforeEach
    void setUp() {
        testUser = new User();

        testUser.setEmail("nek@test.com");
        testUser.setFirstName("Nedyalko");
        testUser.setLastName("Kozaliev");
        testUser.setPassword("password");
        // testUser.setRoles(List.of(clientRole)); to rework it admin
        testUser=userRepository.save(testUser);


    }
    @AfterEach
    void tearDown() {
        driverRepository.deleteAll();
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
        orderRepository.deleteAll();
        carRepository.deleteAll();
        garageRepository.deleteAll();
    }

    @Test
    void NewDriver() throws Exception {
        DriverAddBindingModel testDriverAddBindingModel=new DriverAddBindingModel();
        testDriverAddBindingModel.setFirstName("Test");
        testDriverAddBindingModel.setLastName("Driver");
        testDriverAddBindingModel.setEmail("driver@driver.bg");
        testDriverAddBindingModel.setPassword("12345");
        testDriverAddBindingModel.setConfirmPassword("12345");
        testDriverAddBindingModel.setRegistration("x1234tt");

        mockMvc.perform(
                post("/api/admins/1/createDriver")
                                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDriverAddBindingModel))
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/drivers/")))
                .andExpect(jsonPath("$.firstName").value(is("Test")))
                .andExpect(jsonPath("$.lastName").value(is("Driver")))
                .andExpect(jsonPath("$.email").value(is("driver@driver.bg")))
                .andExpect(jsonPath("$.password").value(is("12345")));


    }

    @Test
    void NewCar() throws Exception {
        CarAddBindingModel testCarAddBindingModel=new CarAddBindingModel();
        testCarAddBindingModel.setRegistration("x9999kk");
        testCarAddBindingModel.setType(CarTypeEnum.valueOf("PassengerCar"));
        testCarAddBindingModel.setKms(12345);
        testCarAddBindingModel.setBrand("Opel");
        testCarAddBindingModel.setModel("astra");
        testCarAddBindingModel.setAddress("some");

        mockMvc.perform(
                post("/api/admins/1/createCar")
                                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCarAddBindingModel))
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/admins/cars/")))
                .andExpect(jsonPath("$.registration").value(is("x9999kk")))
                .andExpect(jsonPath("$.type").value(is("PassengerCar")))
                .andExpect(jsonPath("$kms").value(is("12345")))
                .andExpect(jsonPath("$.brand").value(is("Opel")));

    }


    @Test
    void NewGarage() throws Exception {
        GarageAddBindingModel testGarageAddBindingModel=new GarageAddBindingModel();
        testGarageAddBindingModel.setAddress("some");


        mockMvc.perform(
                post("/api/admins/1/createGarage")
                                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testGarageAddBindingModel))
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/admins/garages/")))
                .andExpect(jsonPath("$.address").value(is("some")));

    }



}