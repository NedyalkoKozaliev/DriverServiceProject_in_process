package com.SoftUni.DriverServiceProject.Service.Impl;

import com.SoftUni.DriverServiceProject.Models.Entity.Order;
import com.SoftUni.DriverServiceProject.Models.ServiceModels.OrderServiceModel;
import com.SoftUni.DriverServiceProject.Models.ViewModel.OrderViewModel;
import com.SoftUni.DriverServiceProject.Repository.OrderRepository;
import com.SoftUni.DriverServiceProject.Service.ClientService;
import com.SoftUni.DriverServiceProject.Service.OrderService;
import com.SoftUni.DriverServiceProject.Service.exeptionHandling.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ClientService clientService;
@Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    this.clientService = clientService;
}


    @Override
    public OrderViewModel createOrder(OrderServiceModel orderServiceModel) {

        Order order=modelMapper.map(orderServiceModel,Order.class);
                //mapAsOr(orderServiceModel);


        order.setClient(clientService.findClientById(orderServiceModel.getClientId()));


        orderRepository.save(order);

        //return mapAsOrder(order);
               return modelMapper.map(order,OrderViewModel.class);
    }

    @Override
    public OrderViewModel getOrderById(Long id){
        return orderRepository.findById(id).map(order -> modelMapper.map(order,OrderViewModel.class)).
                orElseThrow(()->new ObjectNotFoundException("Order with id " + id + " was not found!"))
                ;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public Float takeDistance() {
        return null;
    }

    @Override
    public Float takeDuration() {
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findOrderById(orderId);
    }


    @Override
    public List<OrderViewModel> getAllOrders() {

    List<OrderViewModel> orders=orderRepository.findAll().
            stream().
            map(order->modelMapper.map(order,OrderViewModel.class)).collect(Collectors.toList());
            if (orders.isEmpty()){
                throw new NullPointerException("No pending order");
            }
          return orders;
    }



    private OrderViewModel mapAsOrder(Order order) {
       OrderViewModel orderViewModel = new OrderViewModel();

      orderViewModel.setId(order.getId());
       orderViewModel.setAddressTo(order.getAddressTo());
       orderViewModel.setAddressFrom(order.getAddressFrom());
       orderViewModel.setNumberOfPassengers(order.getNumberOfPassengers());



        return orderViewModel;
    }
    private Order mapAsOr(OrderServiceModel orderServiceModel) {
        Order order=new Order();

       order.setAddressTo(orderServiceModel.getAddressTo());
        order.setAddressFrom(orderServiceModel.getAddressFrom());
        order.setNumberOfPassengers(orderServiceModel.getNumberOfPassengers());
        order.setClient(clientService.findClientById(orderServiceModel.getClientId()));



        return order;
    }
}
