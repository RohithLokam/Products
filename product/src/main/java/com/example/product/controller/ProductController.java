package com.example.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.Pojo.Product;
import com.example.product.Service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController 
{

	private final ProductService productService;

	public ProductController(ProductService productService)
	{
		this.productService=productService;
	}
	@PostMapping("/addproduct")
	public Map<String, Object> addProduct(@RequestBody Product product) 
	{
		return productService.saveProduct(product);
	}

	@PutMapping("/updateproduct")
	public Map<String, Object> updateProduct(@RequestBody Product product)
	{
		return productService.updateProduct(product);

	}

	@DeleteMapping("/deleteproduct")
	public Map<String, Object> deleteProduct(@PathVariable String id)
	{
		return productService.deleteProduct(id);

	}

	@GetMapping("/getallproducts")
	public Map<String, Object> getAllProducts(@RequestBody Product product) 
	{
		return productService.getAllProducts(product);
	}

	@GetMapping("/productsbyid/{id}")
	public Map<String,Object> getdetailsById(@PathVariable String id)
	{
		return productService.getdetailsById(id);
	}

	@GetMapping("/productscount")
	public int getProductCount() {
		return productService.getTotalProductCount();
	}
}

