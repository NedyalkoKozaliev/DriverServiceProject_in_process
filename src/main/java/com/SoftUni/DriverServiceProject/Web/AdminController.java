package com.SoftUni.DriverServiceProject.Web;

import com.SoftUni.DriverServiceProject.Models.Entity.Driver;
import com.SoftUni.DriverServiceProject.Models.ViewModel.AdminViewModel;
import com.SoftUni.DriverServiceProject.Service.AdminService;
import com.SoftUni.DriverServiceProject.Service.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private final ModelMapper modelMapper;
    private final AdminService adminService;
    private final DriverService driverService;

    public AdminController(ModelMapper modelMapper, AdminService adminService, DriverService driverService) {
        this.modelMapper = modelMapper;
        this.adminService = adminService;
        this.driverService = driverService;
    }

    @GetMapping("/{id}/adminDash")
    public String adminsDash(@PathVariable Long id, Model model){

        model
                .addAttribute("admin", modelMapper
                        .map(adminService.findAdminById(id), AdminViewModel.class));

        return "admiDash";
    }
    @GetMapping("/{id}/createDriver") //----------------> отделно с линк в даша

    public String addDriver(@PathVariable Long id, Model model) {

        model
                .addAttribute("admin", modelMapper
                        .map(adminService.findAdminById(id), AdminViewModel.class));

        return "createDriver";
    }


    @GetMapping("/{id}/assignDriverToSubscription") //----------------> в дашборда +++

    public String assignDriverToSub(@PathVariable Long id, Model model) {

        model
                .addAttribute("admin", modelMapper
                        .map(adminService.findAdminById(id), AdminViewModel.class));

        List<Driver> drivers=driverService.getAllDrivers();

        model.addAttribute("drivers",drivers);

        return "assignDriverToSubscription";
    }

    @GetMapping("/{id}/createSubscription") /// ------------------------>отделно с линк в даша

    public String newSubscription(@PathVariable Long id, Model model) {

        model
                .addAttribute("admin", modelMapper
                        .map(adminService.findAdminById(id), AdminViewModel.class));

        return "newSubscription";
    }

    @GetMapping("/{id}/createCar")  /// ------------------------>отделно с линк в даша

    public String createCar(@PathVariable Long id, Model model) {

        model
                .addAttribute("admin", modelMapper
                        .map(adminService.findAdminById(id), AdminViewModel.class));

        return "createCar";
    }

    @GetMapping("/{id}/createGarage")  /// ------------------------>отделно с линк в даша

    public String createGarage(@PathVariable Long id, Model model) {

        model
                .addAttribute("admin", modelMapper
                        .map(adminService.findAdminById(id), AdminViewModel.class));

        return "createGarage";
    }




}
