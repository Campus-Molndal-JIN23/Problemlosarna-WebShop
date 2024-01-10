# <p align="center">The Fantastic Store Of Super Random (stuff)</p>

<p align="center">
<img src="ShopFrontend/src/main/resources/static/SRtransp.gif"/>
</p>


## Description
A fullstack e-commerce, Maven Springboot RESTful API application where you as a registered user can buy <i>super</i> random stuff.

#### if you are not logged in you can:
- View all products.
- View a specific product.
- Register a new user.
- Log in.

When registering as a user the backend will generate a JWT token that will be stored in the frontend until you log out or the token expires.

#### If you log in as a user you can:
- View all products 
- View a specific product
- Add product to your basket.
- Checkout the basket.
- View your order history.

When you add a product to your basket, the backend will generate a new basket for the user and add it to the database.\
When you checkout the basket, the backend will fetch the basket from the database and create a new order for the user.\
When you view your order history the backend will fetch all orders that are connected to your user id and display them.

#### If you log in as an admin you can:
- view all products.
- view a specific product.
- View all users orders.
- Add new items to the shop.
- Update products.
- Delete products.

The backend is built with Java and Springboot, and uses both a MySQL database to store data and an in-memory H2 database for testing.\
It uses Spring Data JPA to communicate with the databases and Spring Security to handle authentication and authorization.

The frontend is built with Thymeleaf, HTML, JS, Bootstrap 5 and CSS.


## What was your motivation?
Our assignment was to build a fullstack application with a RESTful API backend and a frontend that consumes the API.\
We wanted to build something that was close to a real world application and use as many of the technologies we have learned as possible for the backend.\
None of us had any experience with building frontend before this project, so it's been a great learning experience.

## Installation
If you are using IntelliJ IDEA, make sure you are using at least version 2023.2, otherwise errors may occur. If they still do, try clearing IntelliJ:s cache and restart.

To use the application you need to have a local MySQL database running on your computer.
Download MySQL Community from https://dev.mysql.com/downloads/installer/ and install it.
When you have installed MySQL Community you need to create a database called "webshop".
The application uses password "webshopBackend" and username "mYPaSSwoRD!1one" to connect to a local MySQL database.
please alter the password and username in the application.properties file if yours not the same.
You could also mount a Docker image of mySql, for help on how to do this please see the Docker homepage,
or this tutorial https://youtu.be/kphq2TsVRIs?si=wS20hxnnLG2CAxr4

Clone this repository to your computer and open each of the underlying projects in separate windows in your IDE.

## Usage
Start both ShopBackend and ShopFrontend in separate windows in your IDE for best experience. Go to http://localhost:8081/index and register a new user.\

To register a new user you need to fill in the form with a username, password.\
The password needs to be at least 8 characters long and contain at least one uppercase letter, one lowercase letter and one number.\
After registering a new user you will be redirected to the login page.\
Log in with your new user and start shopping!

To log in as an admin use the following credentials:\
username: admin\
password: Password1

## Dependencies
Java 21 
All other dependencies are located in POM-file.

## Credits

Emil Sivertsson - https://github.com/Emilsivertsson  
Wakana Sugihara - https://github.com/83wakasug  
Kristian Karlson - https://github.com/Bremmster  
Cristoffer Östberg - https://github.com/Cristoffer85


### Third party credits
- Thanks to ChatGpt and our rubber-ducks for helping when things got stuck.


## License
MIT

## Badges
![Static Badge](https://img.shields.io/badge/Java_51%25-HTML_47%25-blue)  
![Static Badge](https://img.shields.io/badge/Javascript_1%25-orange)  
![Static Badge](https://img.shields.io/badge/CSS_1%25-green)


## Tests
Unittests - Junit jupiter 5 are used in this project.
Dependencies included in a pom.xml file.

To run, go into src -> test and choose which class you desire to test. Press run on your local IDE and view the progress.
