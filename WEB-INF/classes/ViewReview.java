import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
            Utilities utility= new Utilities(request, pw);
        review(request, response);
    }
    
    protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try
                {           
                response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
                Utilities utility = new Utilities(request,pw);
        if(!utility.isLoggedin()){
            HttpSession session = request.getSession(true);             
            session.setAttribute("login_msg", "Please Login to view Review");
            response.sendRedirect("Login");
            return;
        }
         String productName=request.getParameter("name");        
        HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
        // String userName = "";
        // String reviewRating = "";
        // String reviewDate;
        // String reviewText = "";  
        // String price = "";
        // String city ="";
        String producttype="";
        String productprice="";
        String productmaker="";
        String productrebates="";
        String productOnSale="";
        String userId="";
        String userAge="";
        String userGender="";
        String userOccupation="";
        String storeId="";
        String retailercity="";
        String retailerState="";
        String retailerZip="";
        String reviewRating="";
        String reviewText="";
        String reviewDate="";
                utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
    
                pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Review</a>");
        pw.print("</h2><div class='entry'>");
            
            //if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
        if(hm==null)
        {
        pw.println("<h2>Mongo Db server is not up and running</h2>");
        }
        else
        {
                if(!hm.containsKey(productName)){
                pw.println("<h2>There are no reviews for this product.</h2>");
            }else{
        for (Review r : hm.get(productName)) 
                 {      
        pw.print("<table class='gridtable'>");
                pw.print("<tr>");
                pw.print("<td> Product Name: </td>");
                productName = r.getProductName();
                pw.print("<td>" +productName+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> Product Type: </td>");
                producttype = r.getProductType();
                pw.print("<td>" +producttype+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                
                // pw.print("<td> userName: </td>");
                // userName = r.getUserName();
                // pw.print("<td>" +userName+ "</td>");
                // pw.print("</tr>");
                // pw.print("<tr>");
                pw.print("<td> Product Price: </td>");
                productprice = r.getPrice();
                pw.print("<td>" +productprice+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> Product Maker: </td>");
                productmaker = r.getProductMaker();
                pw.print("<td>" +productmaker+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> Product Rebates: </td>");
                productrebates = r.getProductRebates();
                pw.print("<td>" +productrebates+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> Product On Sale: </td>");
                productOnSale = r.getProductOnSale();
                pw.print("<td>" +productOnSale+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> User Id: </td>");
                userId = r.getUserId();
                pw.print("<td>" +userId+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> User Age: </td>");
                userAge = r.getUserAge();
                pw.print("<td>" +userAge+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> User Gender: </td>");
                userGender = r.getUserGender();
                pw.print("<td>" +userGender+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> User Occupation: </td>");
                userOccupation = r.getUserOccupation();
                pw.print("<td>" +userOccupation+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> Store Id: </td>");
                storeId = r.getStoreId();
                pw.print("<td>" +storeId+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                
                pw.print("<td> Retailer City: </td>");
                retailercity = r.getRetailerCity();
                pw.print("<td>" +retailercity+ "</td>");
                pw.print("</tr>");
                pw.println("<tr>");
                pw.print("<td> Retailer State: </td>");
                retailerState = r.getRetailerState();
                pw.print("<td>" +retailerState+ "</td>");
                pw.print("</tr>");
                pw.println("<tr>");
                pw.print("<td> Retailer Zip: </td>");
                retailerZip= r.getRetailerPin();
                pw.print("<td>" +retailerZip+ "</td>");
                pw.print("</tr>");
                pw.println("<tr>");
                pw.println("<td> Review Rating: </td>");
                reviewRating = r.getReviewRating().toString();
                pw.print("<td>" +reviewRating+ "</td>");
                pw.print("</tr>");
                pw.print("<tr>");
                pw.print("<td> Review Date: </td>");
                reviewDate = r.getReviewDate().toString();
                pw.print("<td>" +reviewDate+ "</td>");
                pw.print("</tr>");          
                pw.print("<tr>");
                pw.print("<td> Review Text: </td>");
                reviewText = r.getReviewText();
                pw.print("<td>" +reviewText+ "</td>");
                pw.print("</tr>");
                pw.println("</table>");
                }                   
                            
        }
        }          
                pw.print("</div></div></div>");     
        utility.printHtml("Footer.html");
                            
                    }
                catch(Exception e)
        {
                 System.out.println(e.getMessage());
        }           
       
        
        }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        
            }
}
