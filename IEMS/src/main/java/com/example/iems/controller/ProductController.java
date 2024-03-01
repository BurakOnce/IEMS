package com.example.iems.controller;

import com.example.iems.dto.CreateProductRequest;
import com.example.iems.dto.UpdateProductRequest;
import com.example.iems.model.Product;
import com.example.iems.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appProduct")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping("/admin/createProduct")
    public ResponseEntity<String> CreateProduct(@RequestBody CreateProductRequest request){
        return productService.CreateProduct(request);
    }

    @PostMapping("/admin/updateProduct")
    public Product UpdateProduct(String barcode, @RequestBody UpdateProductRequest request){
        return productService.updateProduct(barcode,request);
    }

    @DeleteMapping("/admin/deleteProduct")
    public ResponseEntity<String> DeleteProduct(String barcode){
        return productService.deleteProduct(barcode);
    }

    @DeleteMapping("/admin/deleteAllProduct")
    public ResponseEntity<String> DeleteAllProduct(){
        return productService.deleteAllProduct();
    }

    @GetMapping("/admin/sortProduct/byPrice/down")
    public List<Product> SortProductByPriceDown(){
        return productService.sortByPriceDown();
    }
    @GetMapping("/admin/sortProduct/byPrice/up")
    public List<Product> SortProductByPriceUp(){
        return productService.sortByPriceUp();
    }
    @GetMapping("/admin/sortProduct/byDiscount/down")
    public List<Product> SortProductByDiscountDown(){
        return productService.sortByDiscountDown();
    }
    @GetMapping("/admin/sortProduct/byDiscount/up")
    public List<Product> SortProductByDiscountUp(){
        return productService.sortByDiscountUp();
    }


}
