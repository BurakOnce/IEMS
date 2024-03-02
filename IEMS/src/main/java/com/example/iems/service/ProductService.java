package com.example.iems.service;

import com.example.iems.dto.CreateProductRequest;
import com.example.iems.dto.UpdateProductRequest;
import com.example.iems.exceptions.SQLIntegrityConstraintViolationException;
import com.example.iems.model.Product;
import com.example.iems.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService  {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public ResponseEntity<String> CreateProduct(CreateProductRequest request)throws SQLIntegrityConstraintViolationException  {

        try{
        String barcodName = request.getBarcode();
        if (productRepository.findByName(barcodName).isPresent()) {
            return ResponseEntity.ok( "Barcode is already used");
        } else if (request.getBarcode().length()!=8) {
            return ResponseEntity.ok( "Barcode must be 8 digits");
        } else if (request.getDiscount()>=100 || request.getDiscount()<=0) {
            return ResponseEntity.ok( "Discount rate must be between 0 and 100");
        }else {

            Product newProduct = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .stock(request.getStock())
                    .barcode(request.getBarcode())
                    .discount(request.getDiscount())
                    .price(request.getPrice())
                    .build();

            productRepository.save(newProduct);
            return ResponseEntity.ok("Product created successfully ");
            }
        }catch (Exception message){
            throw new SQLIntegrityConstraintViolationException("Make sure that the entered values are unique.");

        }

    }

    public Product updateProduct(String barcode, UpdateProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findByBarcode(barcode);
        if (optionalProduct.isPresent()) {
            Product  existingProduct = optionalProduct.get();

            existingProduct.setName(request.getName() != null ? request.getName() : existingProduct.getName());
            existingProduct.setDescription(request.getDescription() != null ? request.getDescription() : existingProduct.getDescription());
            existingProduct.setStock(request.getStock() != null ? request.getStock() : existingProduct.getStock());
            existingProduct.setBarcode(request.getBarcode() != null ? request.getBarcode() : existingProduct.getBarcode());
            existingProduct.setDiscount(request.getDiscount() != null ? request.getDiscount() : existingProduct.getDiscount());
            existingProduct.setPrice(request.getPrice() != null ? request.getPrice() : existingProduct.getPrice());

            return productRepository.save(existingProduct);
        } else {
            throw new EntityNotFoundException("Product not found 'Barcode' : " + barcode);
        }
    }

    public ResponseEntity<String> deleteProduct(String barcode){
        Optional<Product> optionalProduct = productRepository.findByBarcode(barcode);

        if (optionalProduct.isPresent()){
            Product existingProduct = optionalProduct.get();
            productRepository.delete(existingProduct);
            return ResponseEntity.ok( "Product deleted successfully");

        }else {
            return ResponseEntity.ok( "Product has not found in database");

        }
    }

    public ResponseEntity<String> deleteAllProduct(){
        productRepository.deleteAll();
        return ResponseEntity.ok( "All Product's deleted successfully");

    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> sortByPriceDown(){
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }

    public List<Product> sortByPriceUp(){
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
    }

    public List<Product> sortByDiscountDown(){
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "discount"));
    }

    public List<Product> sortByDiscountUp(){
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "discount"));
    }

}
