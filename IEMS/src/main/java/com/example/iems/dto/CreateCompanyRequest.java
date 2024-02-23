package com.example.iems.dto;


import com.example.iems.model.Product;
import lombok.Builder;
import java.util.List;

@Builder
public record CreateCompanyRequest(
        String name,
        String sector,
        String town,
        String city,
        Long managerId,
        List<Long> employeeId,
        List<Product> productId) {

}
