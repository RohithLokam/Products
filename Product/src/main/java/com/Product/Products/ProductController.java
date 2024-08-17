package com.Product.Products;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/getDetails")
    public Map<String, Object> getProductDetails(@RequestBody Product product) {
    	return productService.getDetails(product);
    }
    
    @GetMapping("/getDetailsById/{productId}")
    public Map<String, Object> getProductDetailsById(@PathVariable String productId) {
    	return productService.getDetailsById(productId);
    }
}