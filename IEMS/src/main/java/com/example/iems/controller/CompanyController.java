package com.example.iems.controller;

import com.example.iems.dto.CreateCompanyRequest;
import com.example.iems.dto.UpdateCompanyRequest;
import com.example.iems.model.Company;
import com.example.iems.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appCompany")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome Burak's App";
    }

    @PostMapping("/admin/createCompany")
    public Company createCompany(@RequestBody CreateCompanyRequest request) {
        return companyService.createCompany(request);
    }

    @PostMapping("/admin/updateCompany")
    public Company updateCompany(String name , @RequestBody UpdateCompanyRequest request){
        return companyService.updateCompany(name,request);
    }
}
