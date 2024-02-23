package com.example.iems.service;

import com.example.iems.dto.CreateCompanyRequest;
import com.example.iems.dto.UpdateCompanyRequest;
import com.example.iems.model.Company;
import com.example.iems.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService  {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(CreateCompanyRequest request) {
        String name = request.name();
        if (companyRepository.findByName(name).isPresent()) {
            return null;
        }

        Company newCompany = Company.builder()
                .name(request.name())
                .sector(request.sector())
                .town(request.town())
                .city(request.city())
                .managerId(request.managerId())
                .employeeId(request.employeeId())
                .productId(request.productId())
                .build();

        return companyRepository.save(newCompany);
    }

/*
    public Company updateCompany(String name, UpdateCompanyRequest request) {
        Optional<Company> optionalCompany = companyRepository.findByName(name);
        if (optionalCompany.isPresent()) {
            Company  existingCompany = optionalCompany.get();

            existingCompany.setName(request.getName() != null ? request.getName() : existingCompany.getName());
            existingCompany.setSector(request.getSector() != null ? request.getSector() : existingCompany.getSector());
            existingCompany.setTown(request.getTown() != null ? request.getTown() : existingCompany.getTown());
            existingCompany.setCity(request.getCity() != null ? request.getCity() : existingCompany.getCity());
            existingCompany.setManager(request.getManager() != null ? request.getManager() : existingCompany.getManager());
            existingCompany.setEmployee(request.getEmployee() != null ? request.getEmployee() : existingCompany.getEmployee());
            existingCompany.setProduct(request.getProduct() != null ? request.getProduct() : existingCompany.getProduct());

            return companyRepository.save(existingCompany);
        } else {
            throw new EntityNotFoundException("Company not found : " + name);
        }
    }*/
}
