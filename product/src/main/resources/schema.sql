-- Create the product table
CREATE TABLE IF NOT EXISTS product (
    id int NOT NULL AUTO_INCREMENT,
    product_id varchar(8) NOT NULL,
    product_name varchar(45) NOT NULL,
    product_category varchar(45) NOT NULL,
    product_description varchar(1000) NOT NULL,
    product_price double NOT NULL,
    product_quantity int NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY product_id_UNIQUE (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DO SLEEP(10);

-- Insert sample records into the product table
INSERT IGNORE INTO product (product_id, product_name, product_category, product_description, product_price, product_quantity) VALUES
('LAP1234', 'Dell XPS 13', 'Laptop', 'The Dell XPS 13 features a sleek design with a 13.3-inch FHD display, Intel Core i7 processor, 16GB RAM, and 512GB SSD. Ideal for professionals and students alike.', 999.99, 20),
('LAP5678', 'MacBook Air', 'Laptop', 'Apple MacBook Air with M1 chip, 13.3-inch Retina display, 8GB RAM, and 256GB SSD. Lightweight and powerful, perfect for on-the-go use.', 1199.99, 15),
('MOB9101', 'Samsung Galaxy S21', 'Mobile', 'The Samsung Galaxy S21 comes with a 6.2-inch Dynamic AMOLED display, Exynos 2100 processor, 8GB RAM, and 128GB storage. Capture stunning photos with its triple camera setup.', 799.99, 25),
('MOB2345', 'iPhone 13', 'Mobile', 'Apple iPhone 13 features a 6.1-inch Super Retina XDR display, A15 Bionic chip, and 128GB storage. Offers exceptional performance and camera capabilities.', 999.99, 30),
('CAM6789', 'Canon EOS R5', 'Camera', 'The Canon EOS R5 is a full-frame mirrorless camera with a 45MP sensor, 8K video recording, and fast autofocus. Ideal for professional photographers.', 3899.99, 10),
('CAM0123', 'Sony Alpha 7 III', 'Camera', 'Sony Alpha 7 III features a 24.2MP full-frame sensor, 4K video recording, and 693-point autofocus system. Great for both photography and videography.', 2299.99, 12),
('MOB4567', 'OnePlus 9', 'Mobile', 'The OnePlus 9 offers a 6.55-inch Fluid AMOLED display, Snapdragon 888 processor, 8GB RAM, and 128GB storage. Enjoy fast performance and a Hasselblad camera system.', 729.99, 20),
('LAP8910', 'HP Spectre x360', 'Laptop', 'HP Spectre x360 2-in-1 laptop with a 13.5-inch OLED display, Intel Core i7 processor, 16GB RAM, and 1TB SSD. Convertible design suitable for any use case.', 1399.99, 18),
('CAM3456', 'Nikon Z6 II', 'Camera', 'Nikon Z6 II is a full-frame mirrorless camera with a 24.5MP sensor, dual EXPEED 6 processors, and 4K UHD video. Perfect for high-quality imaging.', 1999.99, 8),
('MOB7890', 'Google Pixel 6', 'Mobile', 'Google Pixel 6 comes with a 6.4-inch AMOLED display, Google Tensor chip, and 128GB storage. Excellent camera and software experience.', 599.99, 22),
('LAP5432', 'Acer Swift 3', 'Laptop', 'Acer Swift 3 features a 14-inch Full HD display, AMD Ryzen 5 processor, 8GB RAM, and 256GB SSD. Affordable and lightweight laptop for everyday use.', 649.99, 25),
('CAM6780', 'Fujifilm X-T4', 'Camera', 'Fujifilm X-T4 mirrorless camera with a 26.1MP sensor, 4K video recording, and in-body image stabilization. Ideal for both stills and video.', 1699.99, 14),
('MOB1234', 'Xiaomi Mi 11', 'Mobile', 'Xiaomi Mi 11 features a 6.81-inch AMOLED display, Snapdragon 888 processor, and 256GB storage. High performance with a beautiful display.', 749.99, 19),
('LAP8765', 'Lenovo ThinkPad X1 Carbon', 'Laptop', 'Lenovo ThinkPad X1 Carbon offers a 14-inch UHD display, Intel Core i7 processor, 16GB RAM, and 1TB SSD. Durable and high-performance laptop for business.', 1599.99, 12),
('CAM4321', 'Olympus OM-D E-M1 Mark III', 'Camera', 'Olympus OM-D E-M1 Mark III is a micro four-thirds camera with a 20MP sensor, 4K video, and advanced autofocus. Great for travel and professional use.', 1399.99, 17),
('MOB5678', 'Realme GT 2', 'Mobile', 'Realme GT 2 features a 6.62-inch AMOLED display, Snapdragon 888 processor, and 256GB storage. High-speed performance with an attractive design.', 649.99, 21);
