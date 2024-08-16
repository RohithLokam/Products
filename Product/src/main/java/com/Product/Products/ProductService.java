package com.Product.Products;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public Map addDetails(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();		
		response.put("success", false);
		response.put("message", "Unable to connect database!");
		response.put("data", "");
		
		String name = product.getName();
		String productId = generateProductId(name);
		String category = product.getCategory();
		String description = product.getDescription();
		int price = product.getPrice();
		int quantity = product.getQuantity();
		String status = product.getStatus();
		
		if (name == null || "".equalsIgnoreCase(name.trim())) {
			response.put("message", "Please enter product name");
			return response;
		}
		
		//likewise add for all fields to restrict the null data
		try {
		String sql = "insert into products(ProductId,Name,Category,Description,Price,Quantity,Status,CreatedAt) values(?,?,?,?,?,?,?,Now())";
		
		int i = jdbcTemplate.update(sql,productId,name,category,description,price,quantity,status);
		if(i > 0) {
			response.put("success", true);
			response.put("message", "Data inserted successfully!");
		} else {
			response.put("message", "Unable to insert the data!");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return response;
	}
	
	
	public Map updateDetails(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();		
		response.put("success", false);
		response.put("message", "Unable to connect database!");
		response.put("data", "");
		
		String productId = product.getProductId();
		String name = product.getName();
		String category = product.getCategory();
		String description = product.getDescription();
		int price = product.getPrice();
		int quantity = product.getQuantity();
		String status = product.getStatus();
		
		if (name == null || "".equalsIgnoreCase(name.trim())) {
			response.put("message", "Please enter product name");
			return response;
		}
		
		//likewise add for all fields to restrict the null data
		try {
		String sql = "update Products set Name=?,Category=?,Description=?,Price=?,Quantity=?,Status=? where ProductId=?";
		int i = jdbcTemplate.update(sql,name,category,description,price,quantity,status,productId);
		if(i > 0) {
			response.put("success", true);
			response.put("message", "Data updated successfully!");
		} else {
			response.put("message", "Unable to update the data!");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return response;
	}
	
	
	public Map getDetails(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();	
		List<Map<String, Object>> searchResults = null;
		Map eventMap = null;

		response.put("success", false);
		response.put("message", "Unable to connect database!");
		response.put("data", "");
		int offset = 0;
		int limit = 10;
		
		int pageNum = product.getPageNum();
		
		if(pageNum!=0 || pageNum<0) {
			response.put("message", "please provide page number");
		}

		String sql ="select * from Products";
		
		
		
		
		return response;
	}
	
	
	
	
	
	
	
	
	
	
	    private  String generateProductId(String productName) {
	        String productIdPrefix = productName.substring(0, Math.min(4, productName.length())).toUpperCase();

	        Random random = new Random();
	        int randomNumber = 1000 + random.nextInt(9000);

	        return productIdPrefix + randomNumber;
	    }

}
