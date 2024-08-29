# Products Setup Instructions

## Prerequisites
- Make sure you have a MySQL database running.
- Ensure you have the correct JDBC driver for MySQL.

## Configuration
Before running the application, you need to update the database configuration in the `application.properties` file.

1. Open `src/main/resources/application.properties`.

2. Update the following properties to match your local MySQL database setup:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
   spring.datasource.username=your_username
   spring.datasource.password=your_password
