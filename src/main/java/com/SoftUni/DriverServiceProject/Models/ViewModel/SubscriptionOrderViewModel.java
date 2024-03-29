package com.SoftUni.DriverServiceProject.Models.ViewModel;

import com.SoftUni.DriverServiceProject.Models.Entity.Subscription;
import com.SoftUni.DriverServiceProject.Models.Entity.User;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public class SubscriptionOrderViewModel {

    private Long id;
    private String addressFrom;

    private String addressTo;

   private Subscription subscription;

   private BigDecimal price;



    public SubscriptionOrderViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
