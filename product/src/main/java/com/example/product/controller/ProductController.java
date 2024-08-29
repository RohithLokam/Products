package com.example.product.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.product.model.Product;
import com.example.product.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController 
{
	
	@Autowired
	private  ProductService productService;
	
	@PostMapping("/add")
	public Map<String,Object> addProduct(@RequestBody Product product) 
	{
		return productService.saveProduct(product);
	}

	@PutMapping("/update")
	public Map<String, Object> updateProduct(@RequestBody Product product)
	{
		return productService.updateProduct(product);

	}

	@DeleteMapping("/delete/{id}")
	public Map<String,Object> deleteProduct(@PathVariable String id)
	{
		return productService.deleteProduct(id);

	}

	@GetMapping("/get-product-details")
	public Map<String, Object> serachProducts(
			@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="productName",defaultValue="")String productName,	
	        @RequestParam(value="productCategory",defaultValue="")String productCategory)
			
	{
		return productService.searchProducts(page,productName,productCategory);
	}

	@GetMapping("/get-product-details-by-id/{id}")
	public Map<String,Object> getproductById(@PathVariable String id)
	{
		return productService.getproductById(id);
	}

	@GetMapping("/products-count")
	public int getProductCount() {
		return productService.getTotalProductCount();
	}
	
}

