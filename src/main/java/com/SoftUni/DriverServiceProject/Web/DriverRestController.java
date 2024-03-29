package com.SoftUni.DriverServiceProject.Web;


import com.SoftUni.DriverServiceProject.Models.DTO.AssignSubscriptionBindingModel;
import com.SoftUni.DriverServiceProject.Models.Entity.Driver;
import com.SoftUni.DriverServiceProject.Models.ServiceModels.OrderServiceModel;
import com.SoftUni.DriverServiceProject.Models.ViewModel.OrderViewModel;
import com.SoftUni.DriverServiceProject.Models.ViewModel.SubscriptionOrderViewModel;
import com.SoftUni.DriverServiceProject.Service.DriverService;
import com.SoftUni.DriverServiceProject.Service.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class DriverRestController {

    private final ModelMapper modelMapper;
    private  final DriverService driverService;
    private final OrderService orderService;

    public DriverRestController(ModelMapper modelMapper, DriverService driverService, OrderService orderService) {
        this.modelMapper = modelMapper;
        this.driverService = driverService;
        this.orderService = orderService;
    }



    @RequestMapping(value="/api/drivers/{id}/currentOrder",
            produces="application/json",
            method = {RequestMethod.PUT})
    public ResponseEntity<OrderViewModel> AssignOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody @Valid OrderViewModel orderViewModel){

           OrderViewModel currentOrder= driverService.assignOrder(id,orderViewModel.getId());



        URI locationOfCurrentOrder =
                URI.create(String.format("/api/drivers/%s/currentOrder",id));



        return ResponseEntity
                .created(locationOfCurrentOrder)
                .body(currentOrder);}



    @RequestMapping(value="/api/drivers/{id}/currentOrder",
            produces="application/json",
            method = {RequestMethod.GET})
    public ResponseEntity<OrderViewModel> getCurrentTask(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails principal
    ) {
        OrderViewModel orderViewModel=orderService.getOrderById(driverService.findDriverById(id).getCurrentTask().getId());
        return ResponseEntity.ok(orderViewModel);
        
    }

    @RequestMapping(value="/api/drivers/{id}/ordersList",
    produces="application/json",
    method = {RequestMethod.PUT})
    public ResponseEntity<OrderViewModel> FinishOrder(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id,
            @RequestBody @Valid OrderViewModel orderViewModel){

        driverService.finishOrder(id);



        return ResponseEntity.
                noContent().
                build();

    }

    @RequestMapping(value="/api/drivers/{id}/SubscriptionTasks",
            produces="application/json",
            method = {RequestMethod.PUT})
    public ResponseEntity<SubscriptionOrderViewModel> assignTask(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id,
            @RequestBody @Valid AssignSubscriptionBindingModel assignSubscriptionBindingModel){

        driverService.assignSubscription(id,assignSubscriptionBindingModel.getSubscriptionId());



        return ResponseEntity.
                noContent().
                build();

    }

}

