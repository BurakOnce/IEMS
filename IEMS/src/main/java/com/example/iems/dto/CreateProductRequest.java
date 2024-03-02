package com.example.iems.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private Long id;
    private String name;
    private String description;
    private Long stock;
    private String barcode;
    private Long discount;

    private Float price;

}

