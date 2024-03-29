package com.SoftUni.DriverServiceProject.Models.Entity;

import com.SoftUni.DriverServiceProject.Models.Enums.UserRoleEnum;
import jakarta.persistence.*;
import net.bytebuddy.implementation.bind.annotation.Default;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver extends BaseEntity {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;
    @Column
    private Car car;


    private Order currentTask;

    private List<DriverRole> roles ;

    private Set<Order> OrderTasks;

    private Set<SubscriptionOrder> SubscriptionTasks;

    @Column
    private boolean isAvailable;


    public Driver() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @OneToMany(fetch=FetchType.EAGER)
    public Set<Order> getOrderTasks() {
        return OrderTasks;
    }

    public void setOrderTasks(Set<Order> orderTasks) {
        OrderTasks = orderTasks;
    }

    @OneToMany(fetch=FetchType.EAGER)
    public Set<SubscriptionOrder> getSubscriptionTasks() {
        return SubscriptionTasks;
    }

    public void setSubscriptionTasks(Set<SubscriptionOrder> subscriptionTasks) {
        SubscriptionTasks = subscriptionTasks;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<DriverRole> getRoles() {
        return roles;
    }

    public Driver setRoles(List<DriverRole> roles) {
        this.roles = roles;
        return this;
    }

    public Driver addDriverRole(DriverRole role) {
        this.roles.add(role);
        return this;
    }

    @ManyToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @OneToOne
    public Order getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Order currentTask) {
        this.currentTask = currentTask;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
