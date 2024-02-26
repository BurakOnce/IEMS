package com.example.iems.dto;


import com.example.iems.model.Product;
import com.example.iems.model.User;
import lombok.Builder;
import java.util.List;

@Builder
public record CreateCompanyRequest(
        String name,
        String sector,
        String town,
        String city,
        User manager,
        List<User> employee,
        List<Product> product) {

}
