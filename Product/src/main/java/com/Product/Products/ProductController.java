package com.Product.Products;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;


    @PostMapping("/add")
    public Map<String, Object> addProduct(@RequestBody Product product) {
        return productService.addDetails(product);
    }
    
    @PutMapping("/update")
    public Map<String, Object> updateProduct(@RequestBody Product product) {
    	return productService.updateDetails(product);
    }
    
    @PutMapping("/getDetails")
    public Map<String, Object> getProductDetails(@RequestBody Product product) {
    	return productService.getDetails(product);
    }
}