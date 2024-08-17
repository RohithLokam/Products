package com.Product.Products;

import java.util.ArrayList;
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

		// likewise add for all fields to restrict the null data

		try {
			String sql = "insert into products(ProductId,Name,Category,Description,Price,Quantity,Status,CreatedAt) values(?,?,?,?,?,?,?,Now())";

			int i = jdbcTemplate.update(sql, productId, name, category, description, price, quantity, status);
			if (i > 0) {
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

		// likewise add for all fields to restrict the null data
		try {
			String sql = "update Products set Name=?,Category=?,Description=?,Price=?,Quantity=?,Status=? where ProductId=?";
			int i = jdbcTemplate.update(sql, name, category, description, price, quantity, status, productId);
			if (i > 0) {
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
		List mainList = new ArrayList();

		response.put("success", false);
		response.put("message", "Unable to connect database!");
		response.put("data", "");
		int offset = 0;
		int limit = 10;

		Integer pageNum = product.getPageNum();
		String name = product.getName();
		String category = product.getCategory();

		if (pageNum == null) {
			response.put("message", "Please provide a page number.");
		} else if (pageNum <= 0) {
			response.put("message", "Page number must be greater than 0.");
		} else if (pageNum > 1) {
			offset = pageNum - 1 * 10;
		}

		List<Object> params = new ArrayList<>();
		String sql = "select ProductId,Name,Category,Description,Price,Quantity,Status from Products where 1=1 ";

		if (name != null && !name.isEmpty()) {
			sql = sql + "and Name like ? ";
			params.add('%' + name + '%');
		}
		if (category != null && !category.isEmpty()) {
			sql = sql + "and Category like ? ";
			params.add('%' + category + '%');
		}

		sql = sql + "order by Id desc limit ? offset ?";
		params.add(limit);
		params.add(offset);

		try {

			searchResults = jdbcTemplate.queryForList(sql, params.toArray());
			for (Map<String, Object> result : searchResults) {
				Map<String, String> eventMap = new HashMap<>();

				// this is simplified logic
//            String[] specificFields = {"ProductId", "Name", "Category", "Price", "Status", "Quantity"};
//            for (String field : specificFields) {
//                String value = (result.get(field) != null) ? result.get(field).toString() : "";
//                String key = field.toLowerCase();
//                eventMap.put(key, value);
//            }

				String nameValue = (result.get("Name") != null) ? result.get("Name").toString() : "";
				eventMap.put("name", nameValue);
				String productIdValue = (result.get("ProductId") != null) ? result.get("ProductId").toString() : "";
				eventMap.put("productId", productIdValue);

				String categoryValue = (result.get("Category") != null) ? result.get("Category").toString() : "";
				eventMap.put("category", categoryValue);

				String priceValue = (result.get("Price") != null) ? result.get("Price").toString() : "";
				eventMap.put("price", priceValue);

				String descriptionValue = (result.get("Description") != null) ? result.get("Description").toString()
						: "";
				eventMap.put("description", priceValue);

				String statusValue = (result.get("Status") != null) ? result.get("Status").toString() : "";
				eventMap.put("status", statusValue);

				String quantityValue = (result.get("Quantity") != null) ? result.get("Quantity").toString() : "";
				eventMap.put("quantity", quantityValue);

				mainList.add(eventMap);
			}

			response.put("success", true);
			response.put("message", "Data fetched successfully!");
			response.put("data", mainList);

		} catch (Exception e) {
			response.put("message", "Database query failed!");
		}
		return response;
	}

	public Map getDetailsById(String productId) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<Map<String, Object>> searchResults = null;
		List mainList = new ArrayList();
		response.put("success", false);
		response.put("message", "Unable to connect database!");
		response.put("data", "");

		if (productId == null || "".equalsIgnoreCase(productId.trim())) {
			response.put("message", "Please provide product Id");
			return response;
		}

		String sql = "select Name,Category,Description,Price,Quantity,Status from Products where ProductId=? ";

		try {

			searchResults = jdbcTemplate.queryForList(sql, productId);
			for (Map<String, Object> result : searchResults) {
				Map<String, String> eventMap = new HashMap<>();

				// this is simplified logic
//            String[] specificFields = {"ProductId", "Name", "Category", "Price", "Status", "Quantity"};
//            for (String field : specificFields) {
//                String value = (result.get(field) != null) ? result.get(field).toString() : "";
//                String key = field.toLowerCase();
//                eventMap.put(key, value);
//            }

				String nameValue = (result.get("Name") != null) ? result.get("Name").toString() : "";
				eventMap.put("name", nameValue);

				String categoryValue = (result.get("Category") != null) ? result.get("Category").toString() : "";
				eventMap.put("category", categoryValue);

				String priceValue = (result.get("Price") != null) ? result.get("Price").toString() : "";
				eventMap.put("price", priceValue);

				String descriptionValue = (result.get("Description") != null) ? result.get("Description").toString()
						: "";
				eventMap.put("description", priceValue);

				String statusValue = (result.get("Status") != null) ? result.get("Status").toString() : "";
				eventMap.put("status", statusValue);

				String quantityValue = (result.get("Quantity") != null) ? result.get("Quantity").toString() : "";
				eventMap.put("quantity", quantityValue);

				mainList.add(eventMap);
			}

			response.put("success", true);
			response.put("message", "Data fetched successfully!");
			response.put("data", mainList);

		} catch (Exception e) {
			response.put("message", "Database query failed!");
		}
		return response;
	}

	private String generateProductId(String productName) {
		String productIdPrefix = productName.substring(0, Math.min(4, productName.length())).toUpperCase();

		Random random = new Random();
		int randomNumber = 1000 + random.nextInt(9000);

		return productIdPrefix + randomNumber;
	}

}
