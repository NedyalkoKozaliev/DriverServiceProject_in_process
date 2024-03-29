package com.SoftUni.DriverServiceProject.Service.Impl;

import com.SoftUni.DriverServiceProject.Models.Entity.Order;
import com.SoftUni.DriverServiceProject.Models.ServiceModels.OrderServiceModel;
import com.SoftUni.DriverServiceProject.Models.ViewModel.OrderViewModel;
import com.SoftUni.DriverServiceProject.Repository.OrderRepository;
import com.SoftUni.DriverServiceProject.Repository.PriceListRepository;
import com.SoftUni.DriverServiceProject.Service.ClientService;
import com.SoftUni.DriverServiceProject.Service.OrderService;
import com.SoftUni.DriverServiceProject.Service.exeptionHandling.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ClientService clientService;

    private final PriceListRepository priceListRepository;


@Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ClientService clientService, PriceListRepository priceListRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    this.clientService = clientService;
    this.priceListRepository = priceListRepository;

}


    @Override
    public OrderViewModel createOrder(OrderServiceModel orderServiceModel)  {

        Order order=modelMapper.map(orderServiceModel,Order.class);

            BigDecimal price=priceListRepository.findByName("BGNperKm").get().getPrice();

        order.setApproved(false);

        order.setPrice((price.multiply(BigDecimal.valueOf(orderServiceModel.getDistance()))));

        orderRepository.save(order);

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
            stream().filter(order -> !order.isApproved()).
            map(order->modelMapper.map(order,OrderViewModel.class)).collect(Collectors.toList());
            if (orders.isEmpty()){
                throw new NullPointerException("No pending order");
            }
          return orders;
    }


}
