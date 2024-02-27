package com.example.iems.dto;

import com.example.iems.model.Product;
import com.example.iems.model.User;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
@Getter
@Setter
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String description;
    private Long stock;
    private String barcode;
    private Long discount;
    private Float price;
}

