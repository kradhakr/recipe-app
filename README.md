
# Spring Boot - Recipe Application
Build Rest API for Recipe Application

•	Open the project into IntelliJ 
•	Run the RecipeApplication.java can also run using command 
•	Reach till spring boot application folder (recipe-app)
•	Run this command (mvn spring-boot:run)

API Details :
http://localhost:8090/swagger-ui.html#


# Docker packaging
docker build --tag recipe-app .
docker run -p 8080:8090 -t recipe-app --name recipe-app
Access the application using URL : http://192.168.99.100:8080/api/recipe