# 4X - Academic Game in JEE

## Description

4X is an academic game developed in Java EE (JEE) aimed at providing a management and strategy experience. This game was designed to help students become familiar with Java EE programming by utilizing modern development concepts and database management.

## Required Java Version

The project requires **Java 20** to run properly.

## Server Setup

### Tomcat Server

To run the project, you need to deploy the application on a Tomcat server. You can download Tomcat [here](https://tomcat.apache.org/).

## Database Initialization

1. **Create the database:**
   You will find the database creation script in the `src/main/sql` folder. Run this script on your local database to create the necessary tables.

2. **Configure the database connection:**
   Modify the `src/main/java/dao/DbConnection.java` file with your local database information (URL, user, and password).

## Additional Configuration

Don't forget to add the Jakarta Servlet and MySQL Connector libraries to the project's classpath. These libraries are essential for the proper functioning of the application, enabling communication with the server and database.

By following these steps, you should be able to set up and run the 4X game successfully on your local environment.
