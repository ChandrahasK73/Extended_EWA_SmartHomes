import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@WebServlet("/Payment")

public class Payment extends HttpServlet {
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String msg = "good";
		String Customername= "";
		HttpSession session = request.getSession(true);

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to Pay");
			response.sendRedirect("Login");
			return;
		}
		// get the payment details like credit card no address processed from cart servlet	

		String userAddress=request.getParameter("userAddress");
		String creditCardNo=request.getParameter("creditCardNo");
		String purchaseMode = request.getParameter("purchaseMode");
		String storeAddress=request.getParameter("storePickupAddress");
		StringTokenizer st = new StringTokenizer(storeAddress, ",");
		String storeId = st.nextToken();
		String city = request.getParameter("city");
		String state=request.getParameter("state");
		String zipCode=request.getParameter("zipCode");
		String country=request.getParameter("country");
		String productId = request.getParameter("productId");
		String prodType = MySqlDataStoreUtilities.mapOfProds.get(productId);
		String shippingCharges = request.getParameter("shippingCharges");
		int quantity = utility.CartCount();
		double totalSales = 0;
		//System.out.println("Product Id: "+productId);
		if(session.getAttribute("usertype").equals("retailer")){
			Customername =request.getParameter("customername");
			try{
				HashMap<String,User> hm=new HashMap<String,User>();
				hm=MySqlDataStoreUtilities.selectUser();
				if(hm.containsKey(Customername)){
					if(hm.get(Customername).getUsertype().equals("customer")){
						msg ="good";
					}else {msg ="bad";}
						
				}else {msg ="bad";}
				
			}catch(Exception e)
			{
				
			}
		}
		//Date of 5 days later
			Calendar calendar = Calendar.getInstance();
			Date currentDate = calendar.getTime();

			// Add five days to the current date
			calendar.add(Calendar.DAY_OF_MONTH, 5);
			Date fiveDaysLater = calendar.getTime();

			calendar.add(Calendar.DAY_OF_MONTH, 9);
			Date twoWeeksLater = calendar.getTime();

			// Format the dates for display
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String currentDates = dateFormat.format(currentDate);
			String fiveDaysLaterDate = dateFormat.format(fiveDaysLater);
			String twoWeeksLaterDate = dateFormat.format(twoWeeksLater);

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");

		String message=MySqlDataStoreUtilities.getConnection();
		if(message.equals("Successfull"))
		{
			if (msg.equals("good"))
			{
				int orderId=utility.getOrderPaymentSize()+1;
				//iterate through each order
				
				for (OrderItem oi : utility.getCustomerOrders())
				{
					totalSales += oi.getPrice();
				}

				for (OrderItem oi : utility.getCustomerOrders())
				{

					//set the parameter for each column and execute the prepared statement

					utility.storePayment(orderId,oi.getName(),oi.getPrice(),userAddress,creditCardNo,Customername);
					try{
						utility.storeTransaction(utility.username(),utility.username(),userAddress,creditCardNo,orderId,currentDates,twoWeeksLaterDate
												,oi.getName(),prodType,quantity,oi.getPrice(),oi.getPrice()*0.1,10.0,totalSales,storeId,storeAddress);
					}
					catch(Exception e){
						
					}
				}

				//remove the order details from cart after processing
				orderId=utility.getOrderPaymentSize()+1;
				String addressOfProduct = userAddress+", "+city+", "+state+", "+zipCode+", "+country;
					
					pw.print("<h4>Your Order");
					pw.print("&nbsp&nbsp");  
					pw.print("is stored ");
					pw.print("<br>Your Order No is <b>"+(orderId)+"</b></h4>");
					pw.print("<table class='gridtable'><tr><td>");
					if(purchaseMode.equals("HomeDelivery")){
						pw.print("Delivery Address</td><td>"+userAddress+", "+city+", "+state+", "+zipCode+", "+country+"</td></tr>");
						pw.print("<tr><td>Shippin Charges</td><td>"+shippingCharges+"</td>");
					}
					else{
						pw.print("Store Id</td><td>"+storeId+"</td></tr>");
						pw.print("<tr><td>Store Address</td><td>"+st.nextToken()+"</td></tr>");
						pw.print("<tr><td>Store City</td><td>"+st.nextToken()+"</td></tr>");
						pw.print("<tr><td>Store State & zip</td><td>"+st.nextToken()+"</td></tr>");
						//pw.print("<tr><td>Store Zip</td><td>"+st.nextToken()+"</td></tr>");
					}
					pw.print("<tr><td>user ID</td><td>"+utility.username()+"</td>");
					pw.print("<tr><td>credit card number</td><td>"+creditCardNo+"</td>");
					pw.print("<tr><td>order id</td><td>"+orderId+"</td>");
					pw.print("<tr><td>purchase date</td><td>"+currentDates+"</td>");
					if(purchaseMode.equals("HomeDelivery")){
					pw.print("<tr><td>ship date<i>(Expected)</i></td><td>"+twoWeeksLaterDate+"</td>");
					}
					else{
					pw.print("<tr><td>Store Pickup date<i>(Expected)</i></td><td>"+twoWeeksLaterDate+"</td>");

					}
					for (OrderItem oi : utility.getCustomerOrders())
					{

				
					pw.print("<tr><td>Product Id</td><td>"+oi.getName()+"</td>");
					pw.print("<tr><td>Product Price</td><td>"+oi.getPrice()+"</td>");
					pw.print("<tr><td>Product Category</td><td>"+oi.getName()+"</td>");
					
					}
					pw.print("<tr><td>Total sales</td><td>"+totalSales+"</td>");
					
					pw.print("</table>");
					if(purchaseMode.equals("HomeDelivery") && !userAddress.isEmpty())
						pw.print("<h4><br>Your Order is placed on "+(currentDates)+" and is home delivered to address: "+addressOfProduct+" by "+twoWeeksLaterDate);
					else
						pw.print("<br>Your Order is placed on "+(currentDates)+" and is ready to store pickup at address: "+addressOfProduct+" by "+twoWeeksLaterDate);
					pw.print("<br>You can cancel the order latest by "+(fiveDaysLaterDate)+"<h4>");
					pw.print("<br><h5>**Terms & Conditions Apply.</h5>");
					OrdersHashMap.orders.remove(utility.username());
			}else {pw.print("<h2>Customer does not exits</h2>");}		
		}
		else
		{pw.print("<h2>My Sql server is not up and running</h2>");
		
		}
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		
	}
}
