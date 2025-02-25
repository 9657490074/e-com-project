package com.example.ecomproj.controller;

import com.example.ecomproj.model.Product;
import com.example.ecomproj.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> products()
    {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id)
    {
        Product product=productService.getProductByID(id);
        if(product!=null)
        return new ResponseEntity<>(product,HttpStatus.OK);
        else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile)
    {try {
        Product product1 = productService.addProduct(product, imageFile);
        return new ResponseEntity<>(product1,HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/product/{productID}/image")
    public ResponseEntity<byte[]> getImageByProductID(@PathVariable int productID)
    {
        Product product=productService.getProductByID(productID);
        byte[] imageFile=product.getImageDate();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/product/{id}")
    public  ResponseEntity<String> updateProduct(@PathVariable int id,
                                                 @RequestPart Product product,@RequestPart MultipartFile imageFile) throws IOException {
        Product product1=productService.updateProduct(id,product,imageFile);
        {
            if(product1!=null)
            {
                return new ResponseEntity<>("updated",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Not Updated",HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product=productService.getProductByID(id);
        if(product!=null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {

        List<Product> products=productService.searchProduct(keyword);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }



}
