import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import com.mongodb.AggregationOutput;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SalesReport")
public class SalesReport extends HttpServlet {

    static DBCollection myReviews;
    private static final String JSON_FILE_PATH = "C:\\apache-tomcat-9.0.52\\webapps\\Extended_EWA_SmartHomes\\js\\productInventory.json";
    private static final String JSON_SALE_PATH = "C:\\apache-tomcat-9.0.52\\webapps\\Extended_EWA_SmartHomes\\js\\totalSales.json";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayPage(request, response, pw);
    }

    protected void displayPage(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
            throws ServletException, IOException {

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        pw.print("<div id='content'><div class='post'>");
        pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Sales Report</a></h2>"
                + "<div class='entry'>");
        pw.print("<p><b>Product Report Table:</b></p>");
        pw.print("<table class='gridtable'><tr>");
        pw.print("<th>Product Name</th>");   
        pw.print("<th>Product Price</th>");   
        pw.print("<th>Items Sold</th>");   
        pw.print("<th>Total Sales</th></tr>"); 
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray productArray = jsonObject.getAsJsonArray("products");
            
            for (JsonElement element : productArray) {
                pw.print("<tr>");
                JsonObject productObject = element.getAsJsonObject();
                String name = productObject.get("name").getAsString();
                double price = productObject.get("price").getAsDouble();
                int quantity = productObject.get("quantity").getAsInt();
                int totalSold = productObject.get("totalSold").getAsInt();
                double totalSales = productObject.get("totalSales").getAsDouble();
                //products.add(new ProductCount(name, price, quantity, totalSold, totalSales));
                pw.print("<td>"+name+"</td>");
                pw.print("<td>"+price+"</td>");
                pw.print("<td>"+totalSold+"</td>");
                pw.print("<td>"+totalSales+"</td>");
                pw.print("</tr>");
            }
        }
        pw.print("</table><br>"); 
        pw.print("<p><b>Daily Sales Table:</b></p>");
        pw.print("<table class='gridtable'><tr>");
        pw.print("<th>Date</th>");   
        pw.print("<th>Total Sales</th></tr>");
        try (Reader reader = new FileReader(JSON_SALE_PATH)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray salesArray = jsonObject.getAsJsonArray("sales");
            // System.out.println(salesArray);
            for (JsonElement element : salesArray) {
                pw.print("<tr>");
                JsonObject salesObject = element.getAsJsonObject();
                String date = salesObject.get("date").getAsString();
                double totalSales = salesObject.get("totalSales").getAsDouble();
                //sales.add(new SalesCount(date, totalSales));
                pw.print("<td>"+date+"</td>");
                pw.print("<td>"+totalSales+"</td>");
                pw.print("</tr>");
            }
        }
        pw.print("</table>");
        pw.print("<h3><button id='btnGetViewChartData'>View Chart</h3>");
        pw.println("<div id='chart_division'></div>");
        pw.println("</div></div></div>");
        pw.println("<script type='text/javascript' src=\"https://www.gstatic.com/charts/loader.js\"></script>");
        
        pw.println("<script type='text/javascript' src='js/SalesData.js'></script>");


        utility.printHtml("Footer.html");

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ArrayList<Review> reviews = MongoDBDataStoreUtilities.selectReviewForChart();
            ArrayList<Review> topReviewsPerCity = getTop3InEveryCity(reviews);
            
            String reviewJson = new Gson().toJson(topReviewsPerCity);

            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(reviewJson);

        } catch (Exception ex) {
            
        }

    }

    //This method takes all the reviews and returns top 3 review in every city;
    private static ArrayList<Review> getTop3InEveryCity(ArrayList<Review> reviewList){

        //sorting the list in ascending order;
        Collections.sort(reviewList, new Comparator<Review>(){
            public int compare(Review r1, Review r2){
                return Integer.parseInt(r2.getReviewRating()) - Integer.parseInt(r1.getReviewRating());
            }
        });

       HashMap<String,Review> reviewMap = new HashMap<>();

       //Get list of cities in all reviews;
       ArrayList<String> zipCodeList = new ArrayList<>();
       for(Review review:reviewList){
            String zipCode = review.getRetailerPin();
            if(!(zipCodeList.contains(zipCode))){
                zipCodeList.add(zipCode);
            }
       } 

       //get top 3 reviews for every city;
       ArrayList<Review> top3Reviews = new ArrayList<>();
       for(String zipCode:zipCodeList){
            ArrayList<Review> top3ReviewsCity = new ArrayList<>();
            for(Review review:reviewList){
                if(review.getRetailerPin().equals(zipCode) && top3ReviewsCity.size()<3){
                    top3ReviewsCity.add(review);
                }
            }
            top3Reviews.addAll(top3ReviewsCity); 
       }

        return top3Reviews;
    }

}
