import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table  class='gridtable'><tr><td>Customer Name:</td><td>");
		pw.print(userName);
		pw.print("</td></tr>");
		// for each order iterate and display the order name price
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<tr><td> Product Purchased:</td><td>");
			pw.print(oi.getName()+"</td></tr>");
			pw.print("<input type='hidden' name='productId' value='"+oi.getName()+"'>");
        	//pw.print("</td></tr><td>");
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("<td>Product Price:</td><td>"+ oi.getPrice());
			pw.print("</td></tr>");
		}
		pw.print("<tr><td>");
        pw.print("Total Order Cost</td><td>"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
		pw.print("</tr></td>");
		pw.print("<tr><td>");
        pw.print("Shipping Charges(<i>Applied only for home deliveries</i>)</td><td>"+(Double.parseDouble(orderTotal))/10);
		pw.print("<input type='hidden' name='shippingCharges' value='"+((Double.parseDouble(orderTotal))/10)+"'>");
		pw.print("</td></tr></table><table><tr></tr><tr></tr>");	
		pw.print("<tr><td>");
     	pw.print("Credit card no</td>");
		pw.print("<td><input type='text' name='creditCardNo'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("Mode of purchase</td>");
		pw.print("<td><input type='radio' name='purchaseMode' value='StorePickup' checked> Store Pickup");
		pw.print("<input type='radio' name='purchaseMode' value='HomeDelivery'> Home Delivery");
		pw.print("</td></tr>");

		// Add IDs to the address fields for store pickup and home delivery
		pw.print("<table><tr id= 'storePickupFields'><td>");
		pw.print("Store Pickup Address:</td>");
		pw.print("<td>");
		pw.print("<select name='storePickupAddress'>");
		pw.print("<option value='default'></option>");
		pw.print("<option value='Walmart Supercenter, 1234 Elm Street, Springfield, IL 62701.'>Walmart Supercenter, 1234 Elm Street, Springfield, IL 62701.</option>");
		pw.print("<option value='Best Buy Electronics, 567 Oak Avenue, San Francisco, IL 94101.'>Best Buy Electronics, 567 Oak Avenue, San Francisco, IL 94101.</option>");
		pw.print("<option value='Target Store, 789 Maple Road, New York, IL 10001.'>Target Store, 789 Maple Road, New York, IL 10001.</option>");
		pw.print("<option value='Home Depot, 32 Pine Street, Los Angeles, CA 90001.'>Home Depot, 32 Pine Street, Los Angeles, CA 90001.</option>");
		pw.print("<option value='Costco Wholesale, 876 Birch Lane, Chicago, IL 60601.'>Costco Wholesale, 876 Birch Lane, Chicago, IL 60601.</option>");
		pw.print("<option value='Lowe's Home Improvement, 345 Cedar Drive, Miami, FL 33101.'>Lowe's Home Improvement, 345 Cedar Drive, Miami, FL 33101.</option>");
		pw.print("<option value='Macy's Department Store, 210 Oakwood Avenue, Atlanta, GA 30301.'>Macy's Department Store, 210 Oakwood Avenue, Atlanta, GA 30301.</option>");
		pw.print("<option value='CVS Pharmacy, 654 Maplewood Drive, Dallas, TX 75201.'>CVS Pharmacy, 654 Maplewood Drive, Dallas, TX 75201.</option>");
		pw.print("<option value='Barnes & Noble Booksellers, 789 Walnut Street, Seattle, WA 98101.'>Barnes & Noble Booksellers, 789 Walnut Street, Seattle, WA 98101.</option>");
		pw.print("<option value='Bed Bath & Beyond, 987 Cherry Lane, Boston, MA 02101.'>Bed Bath & Beyond, 987 Cherry Lane, Boston, MA 02101.</option>");
		pw.print("</select>");
		pw.print("</td></tr>");

		pw.print("<tr id='homeDeliveryFields'><td>"); // Assign an ID for home delivery fields
		pw.print("Home Delivery Address:</td>");
		pw.print("<td><input type='text' name='homeDeliveryAddress'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");
	    pw.print("Customer Address</td>");
		pw.print("<td><input type='text' name='userAddress'>");
        pw.print("</td></tr>");
		pw.print("<tr><td>");
		pw.print("<tr><td>");
	    pw.print("City</td>");
		pw.print("<td><input type='text' name='city'>");
        pw.print("</td></tr>");
		pw.print("<tr><td>");
	    pw.print("State</td>");
		pw.print("<td><input type='text' name='state'>");
        pw.print("</td></tr>");
		pw.print("<tr><td>");
	    pw.print("Country</td>");
		pw.print("<td><select name='country'>");
		pw.print("<option value='United States'>United States</option>");
		pw.print("<option value='Canada'>Canada</option>");
		pw.print("<option value='Mexico'>Mexico</option>");
		pw.print("</select>");
        pw.print("</td></tr>");
		pw.print("<tr><td>");
	    pw.print("ZipCode</td>");
		pw.print("<td><input type='text' name='zipCode'>");
        pw.print("</td></tr>");
		if(session.getAttribute("usertype").equals("retailer"))
		{
		pw.print("<tr><td>");
	    pw.print("Customer Name</td>");
		pw.print("<td><input type='text' name='customername'>");
        pw.print("</td></tr>");
		}
		pw.print("<tr><td colspan='2'>");
		pw.print("<input type='submit' name='submit' class='btnbuy'>");
        pw.print("</td></tr></table></form>");
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
         System.out.println(e.getMessage());
		}  			
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
