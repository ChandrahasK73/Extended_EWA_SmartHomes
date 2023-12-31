import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public HashMap<String, Integer> noOfItemsSold; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
				result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				if(session.getAttribute("usertype").equals("manager"))
				{
					result = result + "<li><a href='ProductModify?button=Addproduct'><span class='glyphicon'>Addproduct</span></a></li>"
						+ "<li><a href='ProductModify?button=Updateproduct'><span class='glyphicon'>Updateproduct</span></a></li>"
						+"<li><a href='ProductModify?button=Deleteproduct'><span class='glyphicon'>Deleteproduct</span></a></li>"
						//+"<li><a href='DataVisualization'><span class='glyphicon'>Trending</span></a></li>"
						+"<li><a href='DataAnalytics'><span class='glyphicon'>DataAnalytics</span></a></li>"
						+"<li><a href='SalesReport'><span class='glyphicon'>SalesReport</span></a></li>"
						+"<li><a href='Inventory'><span class='glyphicon'>Inventory</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}
				
				else if(session.getAttribute("usertype").equals("retailer")){
					result = result + "<li><a href='Registration'><span class='glyphicon'>Create Customer</span></a></li>"
						+ "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}
				else
				{
				result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			    }
			}
			else
				result = result +"<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
				result = result +"<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul></div></div><div id='page'>";
				pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		try
		{		
			hm=MySqlDataStoreUtilities.selectUser();
		}
		catch(Exception e)
		{
		}	
		User user = hm.get(username());
		return user;
	}
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		int size=0;
		try
		{
			orderPayments =MySqlDataStoreUtilities.selectOrder();
				
		}
		catch(Exception e)
		{
			
		}
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
				size=entry.getKey();
		}
			
		return size;		
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}

	public void storeTransaction(String userId, String customerName, String userAddress, String creditCardNo, int orderId, String purchaseDate, String shipDate, 
									String productId, String productType, int quantity, double orderPrice, double shippingCost, double discount, double totalSales, String storeId, String storeAddress){
										MySqlDataStoreUtilities.insertTransactions(userId, customerName, userAddress, creditCardNo, orderId, purchaseDate, shipDate, 
											 productId, productType, quantity, orderPrice, shippingCost, discount, totalSales, storeId, storeAddress);
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name,String type,String maker, String acc)throws IOException{
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		/* Update the inventory */
		List<ProductCount> products = ProductInventory.loadInventory();
		List<SalesCount> sales = ProductInventory.loadSales();
		double totSales = 0.0;
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDates = dateFormat.format(currentDate);

        for (ProductCount product : products) {
            if (product.getName().equals(name) && product.getQuantity() > 0) {
                product.setQuantity(product.getQuantity() - 1);
                product.setTotalSold(product.getTotalSold() + 1);
                product.setTotalSales(product.getTotalSales() + product.getPrice());
				totSales += product.getPrice();
            }
        }

        ProductInventory.saveInventory(products);
		boolean dateExists = false;
		for(SalesCount sale: sales){
			if(sale.getDate().equals(currentDates)){
				sale.setTotalSales(sale.getTotalSales()+totSales);
				System.out.println("Both dates are equal");
				dateExists = true;
				break;
			}
		}
		if(!dateExists){
			SalesCount newSale = new SalesCount(currentDates, totSales);
			System.out.println("Both dates are not equal");
			sales.add(newSale);
		}
		
				
		ProductInventory.saveSales(sales);

		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		HashMap<String,Console> allconsoles = new HashMap<String,Console> ();
			HashMap<String,Tablet> alltablets = new HashMap<String,Tablet> ();
			HashMap<String,Game> allgames = new HashMap<String,Game> ();
			HashMap<String,Lighting> allLightings = new HashMap<String,Lighting> ();
			HashMap<String,Thermostat> allThermostats = new HashMap<String,Thermostat> ();
			HashMap<String,Accessory> allaccessories=new HashMap<String,Accessory>();
		if(type.equals("consoles")){
			Console console;
			try{
			allconsoles = MySqlDataStoreUtilities.getConsoles();
			// System.out.println("Getting all the consoles that are stored:");
			// for(String str: allconsoles.keySet()){
			// 	System.out.println(str+": "+allconsoles.get(str).getRetailer());
			//}
			}
			catch(Exception e){
				
			}
			console = allconsoles.get(name);
			//System.out.println("name: "+name);
			try{
			OrderItem orderitem = new OrderItem(console.getName(), console.getPrice(), console.getImage(), console.getRetailer());
			orderItems.add(orderitem);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		if(type.equals("games")){
			Game game = null;
			try{
			allgames = MySqlDataStoreUtilities.getGames();
			}
			catch(Exception e){
				
			}
			game = allgames.get(name);
			OrderItem orderitem = new OrderItem(game.getName(), game.getPrice(), game.getImage(), game.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("tablets")){
			Tablet tablet = null;
			try{
			alltablets = MySqlDataStoreUtilities.getTablets();
			}
			catch(Exception e){
				
			}
			tablet = alltablets.get(name);
			OrderItem orderitem = new OrderItem(tablet.getName(), tablet.getPrice(), tablet.getImage(), tablet.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("lighting")){
			Lighting lighting = null;
			try{
			allLightings = MySqlDataStoreUtilities.getLightings();
			}
			catch(Exception e){
				
			}
			lighting = allLightings.get(name);
			OrderItem orderitem = new OrderItem(lighting.getName(), lighting.getPrice(), lighting.getImage(), lighting.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("thermostat")){
			Thermostat thermostat = null;
			try{
			allThermostats = MySqlDataStoreUtilities.getThermostats();
			}
			catch(Exception e){
				
			}
			thermostat = allThermostats.get(name);
			OrderItem orderitem = new OrderItem(thermostat.getName(), thermostat.getPrice(), thermostat.getImage(), thermostat.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("accessories")){	
			try{
			allaccessories = MySqlDataStoreUtilities.getAccessories();
			}
			catch(Exception e){
				
			}
			Accessory accessory = allaccessories.get(name); 
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer());
			orderItems.add(orderitem);
		}
		
	}
	// store the payment details for orders
	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo,String customer){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
			// get the payment details file 
		try
		{
			orderPayments=MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e)
		{
			
		}
		if(orderPayments==null)
		{
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
		if(!orderPayments.containsKey(orderId)){	
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);
		listOrderPayment.add(orderpayment);	
			
			// add order details into database
		try
		{	if(session.getAttribute("usertype").equals("retailer"))
			{
				MySqlDataStoreUtilities.insertOrder(orderId,customer,orderName,orderPrice,userAddress,creditCardNo);
			}else
				
				{MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo);}
		}
		catch(Exception e)
		{
			System.out.println("inside exception file not written properly");
		}	
	}
    
	public String storeReview(String productname,String producttype,String productprice,String productmaker,String productrebates,String reviewrating,String reviewdate,String reviewtext,String retailerPin,String retailerCity,String retailerState,String storeId,String userId,String userAge,String userGender,String userOccupation,String productOnSale){
        String message=MongoDBDataStoreUtilities.insertReview(productname,producttype,productprice,productmaker,productrebates,reviewrating,reviewdate,reviewtext,retailerPin,retailerCity,retailerState,storeId,userId,userAge,userGender,userOccupation,productOnSale);
        if(!message.equals("Successfull"))
        { return "UnSuccessfull";
        }
        else
        {
        HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
        try
        {
            reviews=MongoDBDataStoreUtilities.selectReview();
        }
        catch(Exception e)
        {
            
        }
        if(reviews==null)
        {
            reviews = new HashMap<String, ArrayList<Review>>();
        }
            // if there exist product review already add it into same list for productname or create a new record with product name
            
        if(!reviews.containsKey(productname)){  
            ArrayList<Review> arr = new ArrayList<Review>();
            reviews.put(productname, arr);
        }
        ArrayList<Review> listReview = reviews.get(productname);        
        Review review = new Review(productname,producttype,productprice,productmaker,productrebates,productOnSale,userId,userAge,userGender,userOccupation,storeId,retailerCity,retailerState,retailerPin,reviewrating,reviewdate,reviewtext);
        listReview.add(review); 
            
            // add Reviews into database
        
        return "Successfull";   
        }
    }

	
	/* getConsoles Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Console> getConsoles(){
			HashMap<String, Console> hm = new HashMap<String, Console>();
			hm.putAll(SaxParserDataStore.consoles);
			return hm;
	}
	

	/* getLighting Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, Lighting> getLighting(){
			HashMap<String, Lighting> hm = new HashMap<String, Lighting>();
			hm.putAll(SaxParserDataStore.lightings);
			return hm;
	}

	/* getThermostat Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, Thermostat> getThermostat(){
			HashMap<String, Thermostat> hm = new HashMap<String, Thermostat>();
			hm.putAll(SaxParserDataStore.thermostats);
			return hm;
	}

	/* getGames Functions returns the  Hashmap with all Games in the store.*/

	public HashMap<String, Game> getGames(){
			HashMap<String, Game> hm = new HashMap<String, Game>();
			hm.putAll(SaxParserDataStore.games);
			return hm;
	}
	
	/* getTablets Functions returns the Hashmap with all Tablet in the store.*/

	public HashMap<String, Tablet> getTablets(){
			HashMap<String, Tablet> hm = new HashMap<String, Tablet>();
			hm.putAll(SaxParserDataStore.tablets);
			return hm;
	}
	
	/* getProducts Functions returns the Arraylist of consoles in the store.*/

	public ArrayList<String> getProducts(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Console> entry : getConsoles().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of games in the store.*/

	public ArrayList<String> getProductsGame(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Game> entry : getGames().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of Tablets in the store.*/

	public ArrayList<String> getProductsTablets(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Tablet> entry : getTablets().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	public ArrayList<String> getProductsLighting(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Lighting> entry : getLighting().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public ArrayList<String> getProductsThermostat(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Thermostat> entry : getThermostat().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	

}
