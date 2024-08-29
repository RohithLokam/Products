# ProductsSetup Instructions
Prerequisites
Make sure you have a MySQL database running.
Ensure you have the correct JDBC driver for MySQL.
Configuration
Before running the application, you need to update the database configuration in the application.properties file.

Open src/main/resources/application.properties.

Update the following properties to match your local MySQL database setup:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
Replace your_database_name, your_username, and your_password with your actual database details.

Running the Application
The project is configured to automatically create the necessary tables and insert sample data on the first run. This is controlled by the schema.sql script located in src/main/resources.
Testing
After starting the application, the tables will be created, and sample data will be inserted. You can observe the flow of the project and test its functionality using the provided sample data.
