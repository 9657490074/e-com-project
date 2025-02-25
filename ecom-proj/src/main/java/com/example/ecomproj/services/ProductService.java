package com.example.ecomproj.services;

import com.example.ecomproj.model.Product;
import com.example.ecomproj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();

    }

    public Product getProductByID(int id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return productRepo.save(product);

    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws  IOException{
            product.setImageDate(imageFile.getBytes());
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            return productRepo.save(product);
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
        
    }

    public List<Product> searchProduct(String keyword) {

        return productRepo.searchProduct(keyword);

    }
}
