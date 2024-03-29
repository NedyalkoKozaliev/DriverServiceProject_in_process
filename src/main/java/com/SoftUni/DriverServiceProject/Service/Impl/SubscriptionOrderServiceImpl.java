package com.SoftUni.DriverServiceProject.Service.Impl;

import com.SoftUni.DriverServiceProject.Models.Entity.SubscriptionOrder;
import com.SoftUni.DriverServiceProject.Models.ServiceModels.SubscriptionOrderServiceModel;
import com.SoftUni.DriverServiceProject.Models.ViewModel.SubscriptionOrderViewModel;
import com.SoftUni.DriverServiceProject.Repository.PriceListRepository;
import com.SoftUni.DriverServiceProject.Repository.SubscriptionOrderRepository;
import com.SoftUni.DriverServiceProject.Service.ClientService;
import com.SoftUni.DriverServiceProject.Service.SubscriptionOrderService;
import com.SoftUni.DriverServiceProject.Service.SubscriptionTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionOrderServiceImpl implements SubscriptionOrderService {
    private final ModelMapper modelMapper;
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final ClientService clientService;
    private final SubscriptionTypeService subscriptionTypeService;
    private final PriceListRepository priceListRepository;

    @Autowired
    public SubscriptionOrderServiceImpl(ModelMapper modelMapper, SubscriptionOrderRepository subscriptionOrderRepository, ClientService clientService, SubscriptionTypeService subscriptionTypeService, PriceListRepository priceListRepository) {
        this.modelMapper = modelMapper;
        this.subscriptionOrderRepository = subscriptionOrderRepository;
        this.clientService = clientService;
        this.subscriptionTypeService = subscriptionTypeService;
        this.priceListRepository = priceListRepository;
    }

    @Override
    public SubscriptionOrderViewModel createSubscriptionOrder(SubscriptionOrderServiceModel subscriptionOrderServiceModel) {

        SubscriptionOrder subscriptionOrder=modelMapper.map(subscriptionOrderServiceModel,SubscriptionOrder.class);
        subscriptionOrder.setAssigned(false);
        BigDecimal price=priceListRepository.findByName("BGNperKm").get().getPrice();

        subscriptionOrder.setPrice(BigDecimal.valueOf(subscriptionOrderServiceModel.getDistance()).multiply(BigDecimal.valueOf(subscriptionOrder.getSubscription().getPriceRate()).multiply(price)));

        subscriptionOrderRepository.save(subscriptionOrder);


        return modelMapper.map(subscriptionOrder, SubscriptionOrderViewModel.class);
    }

    @Override
    public List<SubscriptionOrder> findNotAssigned() {

        return subscriptionOrderRepository.findAll().stream().filter(subscriptionOrder -> !subscriptionOrder.isAssigned()).collect(Collectors.toList());

    }

    @Override
    public SubscriptionOrder findSubscriptionOrderById(Long subscriptionId) {
        return subscriptionOrderRepository.findSubscriptionOrderById(subscriptionId);
    }
}
