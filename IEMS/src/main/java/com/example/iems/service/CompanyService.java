package com.example.iems.service;

import com.example.iems.dto.CreateCompanyRequest;
import com.example.iems.dto.UpdateCompanyRequest;
import com.example.iems.model.Company;
import com.example.iems.model.Role;
import com.example.iems.model.User;
import com.example.iems.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService  {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<String> createCompany(CreateCompanyRequest request) {
        String name = request.name();
        if (companyRepository.findByName(name).isPresent()) {
            return ResponseEntity.ok( "Company already exist");
        }

        Company newCompany = Company.builder()
                .name(request.name())
                .sector(request.sector())
                .town(request.town())
                .city(request.city())
                .manager(request.manager())
                .employee(request.employee())
                .product(request.product())
                .build();

        companyRepository.save(newCompany);


        return ResponseEntity.ok( "Company has successfully created");

    }


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
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public void deleteCompany(String name){
        Optional<Company> optionalCompany = companyRepository.findByName(name);

        if (optionalCompany.isPresent()){
            Company existingCompany = optionalCompany.get();
            companyRepository.delete(existingCompany);
            ResponseEntity.ok(" Company deleted from database");

        }else {
            ResponseEntity.ok( "Company has not found in database");

        }
    }

    public List<Company> getCompanyByCity(String city){
        return companyRepository.findCompanyByCity(city);
    }

    public List<Company> getCompanyByTown(String town){
        return companyRepository.findCompanyByTown(town);
    }

    public List<Company> getCompanyBySector(String sector) {
        return companyRepository.findCompanyBySector(sector);
    }

    public Company getCompanyByName(String name) {return companyRepository.findByName(name).orElseThrow();}
}

