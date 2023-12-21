# <p align="center">The Fantastic Store Of Super Random (stuff)</p>

<p align="center">
<img src="ShopFrontend/src/main/resources/static/SRtransp.gif"/>
</p>



## Description
A fullstack e-commerce, Maven Springboot RESTful API application where you as a registered user can buy <i>super</i> random stuff. The application uses both a frontend and a backend in multiple modules.

The backend is built with Java and Springboot and the frontend with Thymeleaf and HTML. The application uses a MySQL database to store data.

Other features in the project are Spring Security, JWT, various Unittests, CSS and Javascript.

## What was your motivation?
Aim to build the first fullstack application as a group. Even though prior small to no experience in frontend before.

* Why did you build this project? (Note: the answer is not "Because it was a homework assignment.")
  What problem does it solve?
  What did you learn?
  Table of Contents (Optional)
  If your README is long, add a table of contents to make it easy for users to find what they need.


## Installation
IF, your using IntelliJ IDEA make sure you are using atleast version 2023.2, otherwise div. errors may occur. If they still do, try clearing IntelliJ:s cache and restart.
  
Important!
if you don't have a local MySQL database running on your computer, the application will not start.
you will get an error message that the application cant connect to the database.\

To use the application you need to have a local MySQL database running on your computer.
Download MySQL Community from https://dev.mysql.com/downloads/installer/ and install it.
When you have installed MySQL Community you need to create a database called "Webshop".
The application uses password "root" and username "root" to connect to a local MySQL database.
please alter the password and username in the application.properties file if yours not the same.
You could also mount a Docker image of mySql, for help on how to do this please see the Docker homepage,
or this tutorial https://youtu.be/kphq2TsVRIs?si=wS20hxnnLG2CAxr4

Clone this repository to your computer and open it in your IDE.

## Usage

Start both ShopBackend and ShopFrontend in separate windows in your IDE for best experience. Go to http://localhost:8081/index and register a new user. Log in and start shopping!

## Credits

Emil Sivertsson - https://github.com/Emilsivertsson  
Wakana Sugihara - https://github.com/83wakasug  
Kristian Karlson - https://github.com/Bremmster  
Cristoffer Östberg - https://github.com/Cristoffer85


* Third-party assets that require attribution?
* Tutorials?

## License
MIT

## Badges
![Static Badge](https://img.shields.io/badge/Java_65%25-HTML_35%25-blue)


## Features
* If your project has a lot of features, list them here.

## Tests
Unittests - Junit jupiter 5 are used in this project.
Dependencies included in a pom.xml file.

To run, go into src -> test and choose which class you desire to test. Press run on your local IDE and view the progress.
