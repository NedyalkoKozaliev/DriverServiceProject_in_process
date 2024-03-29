package com.SoftUni.DriverServiceProject.Service.Impl;

import com.SoftUni.DriverServiceProject.Models.Entity.Subscription;
import com.SoftUni.DriverServiceProject.Models.Enums.SubscriptionEnumName;
import com.SoftUni.DriverServiceProject.Models.ServiceModels.SubscriptionTypeServiceModel;
import com.SoftUni.DriverServiceProject.Models.ViewModel.SubscriptionTypeViewModel;
import com.SoftUni.DriverServiceProject.Repository.SubscriptionRepository;
import com.SoftUni.DriverServiceProject.Service.SubscriptionTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final ModelMapper modelMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionTypeServiceImpl(ModelMapper modelMapper, SubscriptionRepository subscriptionRepository) {
        this.modelMapper = modelMapper;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public SubscriptionTypeViewModel changeType(SubscriptionTypeServiceModel subscriptionTypeServiceModel) {
        Subscription subscription=subscriptionRepository.findByName(subscriptionTypeServiceModel.getName()).orElse(null);
        subscription.setDescription(subscriptionTypeServiceModel.getDescription());
        subscription.setPriceRate(subscriptionTypeServiceModel.getPriceRate());
        subscriptionRepository.save(subscription);
        return modelMapper.map(subscription,SubscriptionTypeViewModel.class);

    }

    @Override
    public Subscription getSubscriptionByName(SubscriptionEnumName subscription) {
        Subscription subscription1=subscriptionRepository.findByName(subscription).orElse(null);
        return subscription1;
    }
}
