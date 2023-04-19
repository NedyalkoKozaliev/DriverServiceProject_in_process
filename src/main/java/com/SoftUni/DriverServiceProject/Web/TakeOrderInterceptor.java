package com.SoftUni.DriverServiceProject.Web;

import com.SoftUni.DriverServiceProject.Models.Entity.Driver;
import com.SoftUni.DriverServiceProject.Service.DriverService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class TakeOrderInterceptor implements HandlerInterceptor {

    private  final DriverService driverService;

    public TakeOrderInterceptor(DriverService driverService) {
        this.driverService = driverService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Driver driver=driverService.findByEmail(request.getUserPrincipal().getName());
        //Driver driver = (Driver) request.getUserPrincipal();

        System.out.println("in the interceptor");
        if (!driver.isAvailable()) {
            response.sendRedirect("/youAreBusy");

        }
        return true;
    }

}



