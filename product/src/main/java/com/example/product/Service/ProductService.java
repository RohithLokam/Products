package com.example.product.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.product.Pojo.Product;

@Service
public class ProductService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Add the product details
	public Map<String, Object> saveProduct(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "Unable to connect database");

		if (product.getProductName() == null || "".equalsIgnoreCase(product.getProductName().trim())) {
			response.put("message", "Please enter product name");
			return response;
		} else if (product.getProductCategory() == null || "".equalsIgnoreCase(product.getProductCategory().trim())) {
			response.put("message", "please enter product category");
			return response;
		} else if (product.getProductDescription() == null
				|| "".equalsIgnoreCase(product.getProductDescription().trim())) {
			response.put("message", "please enter product description");
			return response;
		} else if (product.getProductPrice() <= 0) {
			response.put("message", "please enter valid product price");
			return response;
		} else if (product.getProductQuantity() <= 0) {
			response.put("message", "please enter valid  product quantity");
			return response;
		}
		try {
			String sql = "insert into product(product_id,product_name,product_category,product_description,product_price,product_quantity) values(?,?,?,?,?,?)";
			int i = jdbcTemplate.update(sql, generateProductId(product.getProductName()), product.getProductName(),
					product.getProductCategory(), product.getProductDescription(), product.getProductPrice(),
					product.getProductQuantity());
			if (i > 0) {
				response.put("result", true);
				response.put("message", "product details added");
			} else {
				response.put("message", "product details unable to add");
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

		if (product.getProductName() == null || "".equalsIgnoreCase(product.getProductName().trim())) {
			response.put("message", "Please enter product name");
			return response;
		}

		else if (product.getProductCategory() == null || "".equalsIgnoreCase(product.getProductCategory().trim())) {
			response.put("message", "please enter product category");
			return response;
		}

		else if (product.getProductDescription() == null
				|| "".equalsIgnoreCase(product.getProductDescription().trim())) {
			response.put("message", "please enter product description");
			return response;
		}

		else if (product.getProductPrice() <= 0) {
			response.put("message", "please enter valid product price");
			return response;

		}

		else if (product.getProductQuantity() <= 0) {
			response.put("message", "please enter valid product quantity");
			return response;
		}

		try {
			String sql = "update product set product_name=?,product_category=?,product_description=?,product_price=?,product_quantity=? where product_id=?";
			int i = jdbcTemplate.update(sql, product.getProductName(), product.getProductCategory(),
					product.getProductDescription(), product.getProductPrice(), product.getProductQuantity(),
					product.getProductId());
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
	public Map<String, Object> deleteProduct(String id) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "unable to connect db");

		try {
			String sql = "delete from product where product_id=?";
			int i = jdbcTemplate.update(sql, id);
			if (i > 0) {
				response.put("result", true);
				response.put("message", "product details deleted");
			}

			else {
				response.put("message", "please enter valid productId");
			}
		} catch (Exception e) {
			e.getMessage();
			response.put("error", e.getMessage());
		}
		return response;

	}

	// Get all the product details
	public Map<String, Object> getAllProducts(Product product) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", false);
		response.put("message", "unable to connect db");
		response.put("data", "");
		int offset = 0;
		int limit = 10;

		Integer pageNum = product.getPageNum();
		String name = product.getProductName();
		String category = product.getProductCategory();

//		if (pageNum == null) {
//			response.put("message", "Please provide a page number.");
//		}

		if (pageNum <= 0) {
			response.put("message", "Page number must be greater than 0.");
		} else if (pageNum > 1) {
			offset = pageNum - 1 * 10;
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		try {
			List<Object> params = new ArrayList<>();
			String sql = " select * from product where 1=1 ";

			if (name != null && !name.isEmpty()) {
				sql = sql + "and Name like ? ";
				params.add('%' + name + '%');
			}
			if (category != null && !category.isEmpty()) {
				sql = sql + "and Category like ? ";
				params.add('%' + category + '%');
			}

			sql = sql + "order by Id desc limit ? offset ? order by Id desc";
			params.add(limit);
			params.add(offset);
			result = jdbcTemplate.queryForList(sql);
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
	public Map<String, Object> getdetailsById(String productId) {
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

	// generate random number for productId
	private String generateProductId(String productName) {
		// Get up to the first 4 uppercase characters of productName
		String productIdPrefix = productName.substring(0, Math.min(4, productName.length())).toUpperCase();
		Random random = new Random();
		int randomNumber = 1000 + random.nextInt(9000);
		return productIdPrefix + randomNumber;
	}

}
