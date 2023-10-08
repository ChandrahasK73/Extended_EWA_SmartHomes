# Installation Guide

Follow these steps to set up and run the application on your local system.

## Step 1: Install Tomcat Apache Server

Download and install Tomcat Apache Server as your application server.

## Step 2: Install MySQL

Download and install MySQL on your local system.

## Step 3: Create a MySQL Database

Create a database named `exampledatabase1`.

## Step 4: Create Database Tables

In the `exampledatabase1`, create five tables: `Registration`, `ProductDetails`, `CustomerOrders`, `Transactions`, and `Stores`. Use the "Create Table" query with the required fields for user details, products, and orders.

## Step 5: Install MongoDB

Download and install MongoDB database server on your local machine. This is required for storing product reviews.

## Step 6: Create a MongoDB Database

Create a database using the command `use CustomerReviewsDb`.

## Step 7: Create a MongoDB Collection

Create a collection under the `CustomerReviewsDb` database using the command `db.createCollection("myReviews")`.

## Step 8: Deploy Application

Copy and paste the folder named `Extended_EWA_SmartHomes` into `C:\Tomcat\apache-tomcat-7.0.34\webapps`.

## Step 9: Start Tomcat Server

Start the Tomcat Apache server and open a web browser. Access the application by entering the folder name in the URL followed by "localhost." For example, `localhost/Extended_EWA_SmartHomes`.

## Step 10: Application is Live

The application is now live, and you can access and use all the functionalities as discussed in Assignment 2. Enjoy using the application!
