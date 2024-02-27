package com.example.iems.dto;


import lombok.Builder;



@Builder
public record CreateProductRequest(

        Long id,
        String name,
        String description,
        Long stock,
        String barcode,
        Long discount,
        Float price
){
}
