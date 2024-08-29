package com.example.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.product.model.Product;

@Service
public class ProductService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Add the product details
	public Map<String, Object> saveProduct(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "Unable to connect database");

		String productName = product.getProductName();
		String productCategory = product.getProductCategory();
		String productDescription = product.getProductDescription();
		double productPrice = product.getProductPrice();
		int productQuantity = product.getProductQuantity();

		String productId = generateProductId(productName);

		if (productName == null || "".equalsIgnoreCase(productName.trim()) || (isStringContainsSpecialChars(productName))) {
			response.put("message", "Please enter valid product name");
			return response;
		} else if (productCategory == null || "".equalsIgnoreCase(productCategory.trim()) || (isStringContainsSpecialChars(productCategory))) {
			response.put("message", "please enter valid product category");
			return response;
		} else if (productDescription == null || "".equalsIgnoreCase(productDescription.trim())) {
			response.put("message", "please enter valid product description");
			return response;
		} else if (productPrice <= 0) {
			response.put("message", "please enter valid product price");
			return response;
		} else if (productQuantity <= 0) {
			response.put("message", "please enter valid  product quantity");
			return response;
		}
		try {
			String sql = "insert into product(product_id,product_name,product_category,product_description,product_price,product_quantity) values(?,?,?,?,?,?)";
			int i = jdbcTemplate.update(sql, productId, productName, productCategory, productDescription, productPrice, productQuantity);
			if (i > 0) {
				response.put("result", true);
				response.put("message", "product details added");
			} else {
				response.put("message", "product unable to add");
			}
		} catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}

	// update the product details
	public Map<String, Object> updateProduct(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "Unable to connect database!");
		
		String productName = product.getProductName();
		String productId = product.getProductId();
		String productCategory = product.getProductCategory();
		String productDescription = product.getProductDescription();
		double productPrice = product.getProductPrice();
		int productQuantity = product.getProductQuantity();
		
		if (productId == null || "".equalsIgnoreCase(productId.trim())) {
			response.put("message", "Please enter product Id!");
			return response;
		} else if (productName == null || "".equalsIgnoreCase(productName.trim()) || (isStringContainsSpecialChars(productName))) {
			response.put("message", "Please enter product name");
			return response;
		} else if (productCategory == null || "".equalsIgnoreCase(productCategory.trim()) || (isStringContainsSpecialChars(productCategory))) {
			response.put("message", "please enter product category");
			return response;
		} else if (productDescription == null	|| "".equalsIgnoreCase(productDescription.trim())) {
			response.put("message", "please enter product description");
			return response;
		} else if (productPrice <= 0) {
			response.put("message", "please enter valid product price");
			return response;
		} else if (productQuantity <= 0) {
			response.put("message", "please enter valid product quantity");
			return response;
		}
		
		try {
			String sql = "update product set product_name=?,product_category=?,product_description=?,product_price=?,product_quantity=? where product_id=?";
			int i = jdbcTemplate.update(sql, productName, productCategory,productDescription, productPrice, productQuantity, productId);
			if (i > 0) {
				response.put("message", "product details updated");
			} else {
				response.put("message", "product details unable to update");
			}
		} catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}

	// delete the product details
	public Map<String, Object> deleteProduct(String productId) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "unable to connect db");

		try {
			String sql = "delete from product where product_id=?";
			int i = jdbcTemplate.update(sql, productId);
			if (i > 0) {
				response.put("result", true);
				response.put("message", "product details deleted");
			}
			else {
				response.put("message", "product details unable to delete");
			}
		} catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}

	// search products by product name or product category
	public Map<String, Object> searchProducts(int pageNum, String productName, String productCategory) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "unable to connect db");
		response.put("data", "");
		
		int offset = (pageNum - 1) * 10;

		List<Map<String, Object>> result = null;
		List<Object> params = new ArrayList<>();

		try {
			String sql = "SELECT product_id,product_name,product_category,product_description,product_price,product_quantity FROM product where 1=1";

			if (productName != null && !productName.isEmpty()) {
				sql += " AND product_name like  ?";
				params.add('%' + productName + '%');
			}
			if (productCategory != null && !productCategory.isEmpty()) {
				sql += " AND product_category like ?";
				params.add("%" + productCategory + "%");
			}
			sql += " order by id desc limit 10 offset ?";
			params.add(offset);

			result = jdbcTemplate.queryForList(sql, params.toArray());

			if (!result.isEmpty()) {
				response.put("result", true);
				response.put("message", "product details fetched successfully!");
				response.put("data", result);
			} else {
				response.put("result", false);
				response.put("message", "unable to fetched the product details!");
			}
		} catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}

	// get the product based on Id
	public Map<String, Object> getproductById(String productId) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> searchResults = null;
		response.put("success", false);
		response.put("message", "Unable to connect database!");
		response.put("data", "");
		
		if (productId == null || "".equalsIgnoreCase(productId.trim())) {
			response.put("message", "Please provide product Id");
			return response;
		}
		
		try {
			String sql = "select product_name,product_category,product_description,product_price,product_quantity from product where product_id=?";
			searchResults = jdbcTemplate.queryForList(sql, productId);
			if (!searchResults.isEmpty()) {
				response.put("success", true);
				response.put("message", "Data fetched successfully!");
				response.put("data", searchResults);
			} else {
				response.put("message", "Unable to fetched details!");
			}
		} catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;
	}

	// Count the number of products
	public int getTotalProductCount() {
		String sql = "SELECT COUNT(*) FROM product";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	// special character checking
	private static boolean isStringContainsSpecialChars(String str) {
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}

	// generate random number for productId
	private String generateProductId(String productName) {
		String productIdPrefix = productName.substring(0, Math.min(4, productName.length())).toUpperCase();
		Random random = new Random();
		int randomNumber = 1000 + random.nextInt(9000);
		return productIdPrefix + randomNumber;
	}
}
