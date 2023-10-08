import java.sql.*;
import java.util.*;

public class MySqlDataStoreUtilities
{
	static Connection conn = null;
	static String message;
	public static HashMap<String,String> mapOfProds = new HashMap<String, String>();
	public static String getConnection()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledatabase1","root","root");							
			message="Successfull";
			return message;
		}
		catch(SQLException e)
		{
			message="unsuccessful";
			return message;
		}
		catch(Exception e)
		{
			message=e.getMessage();
			return message;
		}
	}
	public static  void insertTransactions(String userId, String customerName, String userAddress, String creditCardNo, int orderId, String purchaseDate, String shipDate, 
									String productId, String category, int quantity, double orderPrice, double shippingCost, double discount, double totalSales, String storeId, String storeAddress){
								
			
			
			try{
				String insertTransactionQurey = "INSERT INTO  Transactions(userId, customerName, customerAddress, creditCardNo, orderId, purchaseDate, shipDate, productId, category, quantity, orderPrice, shippingCost, discount, totalSales, storeId, storeAddress)" +"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			  
				
				
				PreparedStatement pst = conn.prepareStatement(insertTransactionQurey);
				pst.setString(1,userId);
				pst.setString(2,customerName);
				pst.setString(3,userAddress);
				pst.setString(4,creditCardNo);
				pst.setInt(5,orderId);
				pst.setString(6,purchaseDate);
				pst.setString(7,shipDate);
				pst.setString(8,productId);
				pst.setString(9,category);
				pst.setDouble(10,quantity);
				pst.setDouble(11,orderPrice);
				pst.setDouble(12,shippingCost);
				pst.setDouble(13,discount);
				pst.setDouble(14,totalSales);

				pst.setString(15,storeId);
				pst.setString(16,storeAddress);

				
				pst.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
				
	}
	public static void Insertproducts()
	{
		try{
			
			
			getConnection();
			
			
			String truncatetableacc = "delete from Product_accessories;";
			PreparedStatement pstt = conn.prepareStatement(truncatetableacc);
			pstt.executeUpdate();
			
			String truncatetableprod = "delete from  Productdetails;";
			PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
			psttprod.executeUpdate();

			String truncatetablestore = "delete from  Stores;";
			PreparedStatement pststore = conn.prepareStatement(truncatetablestore);
			pststore.executeUpdate();
			
			
			
			String insertProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productDescription,productWarranty,productRebates)" +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?);";
			for(Map.Entry<String,Accessory> entry : SaxParserDataStore.accessories.entrySet())
			{   
				String name = "accessories";
				Accessory acc = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,acc.getId());
				pst.setString(3,acc.getName());
				pst.setDouble(4,acc.getPrice());
				pst.setString(5,acc.getImage());
				pst.setString(6,acc.getRetailer());
				pst.setString(7,acc.getCondition());
				pst.setDouble(8,acc.getDiscount());
				pst.setString(9,acc.getDescription());
				pst.setString(10,acc.getWarranty());
				pst.setString(11,acc.getRebates());
				mapOfProds.put(acc.getName(), name);
				
				pst.executeUpdate();
				
				
			}
			
			for(Map.Entry<String,Console> entry : SaxParserDataStore.consoles.entrySet())
			{   
				Console con = entry.getValue();
				String name = "consoles";
				
				
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,con.getId());
				pst.setString(3,con.getName());
				pst.setDouble(4,con.getPrice());
				pst.setString(5,con.getImage());
				pst.setString(6,con.getRetailer());
				pst.setString(7,con.getCondition());
				pst.setDouble(8,con.getDiscount());
				pst.setString(9,con.getDescription());
				pst.setString(10,con.getWarranty());
				pst.setString(11,con.getRebates());
				mapOfProds.put(con.getName(), name);
				
				pst.executeUpdate();
				try{
					HashMap<String,String> acc = con.getAccessories();
					String insertAccessoryQurey = "INSERT INTO  Product_accessories(productName,accessoriesName)" +
					"VALUES (?,?);";
					for(Map.Entry<String,String> accentry : acc.entrySet())
					{
						PreparedStatement pstacc = conn.prepareStatement(insertAccessoryQurey);
						pstacc.setString(1,con.getId());
						pstacc.setString(2,accentry.getValue());
						pstacc.executeUpdate();
					}
				}catch(Exception et){
					et.printStackTrace();
				}
			}
			for(Map.Entry<String,Game> entry : SaxParserDataStore.games.entrySet())
			{   
				String name = "games";
				Game game = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,game.getId());
				pst.setString(3,game.getName());
				pst.setDouble(4,game.getPrice());
				pst.setString(5,game.getImage());
				pst.setString(6,game.getRetailer());
				pst.setString(7,game.getCondition());
				pst.setDouble(8,game.getDiscount());
				pst.setString(9,game.getDescription());
				pst.setString(10,game.getWarranty());
				pst.setString(11,game.getRebates());
				mapOfProds.put(game.getName(), name);
				
				pst.executeUpdate();
				
				
			}
			for(Map.Entry<String,Tablet> entry : SaxParserDataStore.tablets.entrySet())
			{   
				String name = "tablets";
				Tablet tablet = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,tablet.getId());
				pst.setString(3,tablet.getName());
				pst.setDouble(4,tablet.getPrice());
				pst.setString(5,tablet.getImage());
				pst.setString(6,tablet.getRetailer());
				pst.setString(7,tablet.getCondition());
				pst.setDouble(8,tablet.getDiscount());
				pst.setString(9,tablet.getDescription());
				pst.setString(10,tablet.getWarranty());
				pst.setString(11,tablet.getRebates());
				mapOfProds.put(tablet.getName(), name);
				
				pst.executeUpdate();
				
				
			}
			for(Map.Entry<String,Thermostat> entry : SaxParserDataStore.thermostats.entrySet())
			{   
				String name = "thermostat";
				Thermostat thermostat = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,thermostat.getId());
				pst.setString(3,thermostat.getName());
				pst.setDouble(4,thermostat.getPrice());
				pst.setString(5,thermostat.getImage());
				pst.setString(6,thermostat.getRetailer());
				pst.setString(7,thermostat.getCondition());
				pst.setDouble(8,thermostat.getDiscount());
				pst.setString(9,thermostat.getDescription());
				pst.setString(10,thermostat.getWarranty());
				pst.setString(11,thermostat.getRebates());
				mapOfProds.put(thermostat.getName(), name);
				
				pst.executeUpdate();
				
				
			}
			for(Map.Entry<String,Lighting> entry : SaxParserDataStore.lightings.entrySet())
			{   
				String name = "lighting";
				Lighting lighting = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertProductQurey);
				pst.setString(1,name);
				pst.setString(2,lighting.getId());
				pst.setString(3,lighting.getName());
				pst.setDouble(4,lighting.getPrice());
				pst.setString(5,lighting.getImage());
				pst.setString(6,lighting.getRetailer());
				pst.setString(7,lighting.getCondition());
				pst.setDouble(8,lighting.getDiscount());
				pst.setString(9,lighting.getDescription());
				pst.setString(10,lighting.getWarranty());
				pst.setString(11,lighting.getRebates());
				mapOfProds.put(lighting.getName(), name);
				
				pst.executeUpdate();
				
				
			}

			String insertStoresQuery = "INSERT INTO  Stores(storeId,name,street,city,state,zipCode)" +
			"VALUES (?,?,?,?,?,?);";
			for(Map.Entry<String,Store> entry : SaxParserDataStore.stores.entrySet())
			{   
				
				Store store = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertStoresQuery);
				pst.setString(1,store.getId());
				pst.setString(2,store.getName());
				pst.setString(3,store.getStreet());
				pst.setString(4,store.getCity());
				pst.setString(5,store.getState());
				pst.setString(6,store.getZipcode());
				
				pst.executeUpdate();
				
				
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	} 

	public static HashMap<String,Console> getConsoles()
	{	
		HashMap<String,Console> hm=new HashMap<String,Console>();
		try 
		{
			getConnection();
			
			String selectConsole="select * from  Productdetails where ProductType=?";
			PreparedStatement pst = conn.prepareStatement(selectConsole);
			pst.setString(1,"consoles");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
				{	Console con = new Console(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productDescription"),rs.getString("productWarranty"),rs.getString("productRebates"));
				//System.out.println(rs.getString("productName")+"\n"+rs.getDouble("productPrice")+"\n"+rs.getString("productImage")+"\n"+rs.getString("productManufacturer")+"\n"+rs.getString("productCondition")+"\n"+rs.getDouble("productDiscount"));
			hm.put(rs.getString("Id"), con);
			//System.out.println(rs.getString("Id"));
			con.setId(rs.getString("Id"));
			
			try
			{
				String selectaccessory = "Select * from Product_accessories where productName=?";
				PreparedStatement pstacc = conn.prepareStatement(selectaccessory);
				pstacc.setString(1,rs.getString("Id"));
				ResultSet rsacc = pstacc.executeQuery();
				
				HashMap<String,String> acchashmap = new HashMap<String,String>();
				while(rsacc.next())
				{    
					if (rsacc.getString("accessoriesName") != null){
						
						acchashmap.put(rsacc.getString("accessoriesName"),rsacc.getString("accessoriesName"));
						con.setAccessories(acchashmap);
					}
					
				}					
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Tablet> getTablets()
{	
	HashMap<String,Tablet> hm=new HashMap<String,Tablet>();
	try 
	{
		getConnection();
		
		String selectTablet="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectTablet);
		pst.setString(1,"tablets");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Tablet tab = new Tablet(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productDescription"),rs.getString("productWarranty"),rs.getString("productRebates"));
		hm.put(rs.getString("Id"), tab);
		tab.setId(rs.getString("Id"));

	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Game> getGames()
{	
	HashMap<String,Game> hm=new HashMap<String,Game>();
	try 
	{
		getConnection();
		
		String selectGame="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectGame);
		pst.setString(1,"games");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Game game = new Game(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productDescription"),rs.getString("productWarranty"),rs.getString("productRebates"));
		hm.put(rs.getString("Id"), game);
		game.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Thermostat> getThermostats()
{	
	HashMap<String,Thermostat> hm=new HashMap<String,Thermostat>();
	try 
	{
		getConnection();
		
		String selectThermostat="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectThermostat);
		pst.setString(1,"thermostat");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Thermostat thermostat = new Thermostat(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productDescription"),rs.getString("productWarranty"),rs.getString("productRebates"));
		hm.put(rs.getString("Id"), thermostat);
		thermostat.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Lighting> getLightings()
{	
	HashMap<String,Lighting> hm=new HashMap<String,Lighting>();
	try 
	{
		getConnection();
		
		String selectLighting="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectLighting);
		pst.setString(1,"lighting");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Lighting lighting = new Lighting(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productDescription"),rs.getString("productWarranty"),rs.getString("productRebates"));
		hm.put(rs.getString("Id"), lighting);
		lighting.setId(rs.getString("Id"));
	}
}
catch(Exception e)
{
}
return hm;			
}

public static HashMap<String,Accessory> getAccessories()
{	
	HashMap<String,Accessory> hm=new HashMap<String,Accessory>();
	try 
	{
		getConnection();
		
		String selectAcc="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectAcc);
		pst.setString(1,"accessories");
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
			{	Accessory acc = new Accessory(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productDescription"),rs.getString("productWarranty"),rs.getString("productRebates"));
		hm.put(rs.getString("Id"), acc);
		acc.setId(rs.getString("Id"));

		}
	}
catch(Exception e)
{
}
return hm;			
}

public static String addproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String prod,String productDescription,String productWarranty,String productRebates)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productDescription,productWarranty,productRebates)" +
		"VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		
		String name = producttype;
		
		PreparedStatement pst = conn.prepareStatement(addProductQurey);
		pst.setString(1,name);
		pst.setString(2,productId);
		pst.setString(3,productName);
		pst.setDouble(4,productPrice);
		pst.setString(5,productImage);
		pst.setString(6,productManufacturer);
		pst.setString(7,productCondition);
		pst.setDouble(8,productDiscount);
		pst.setString(9,productDescription);
		pst.setString(10,productWarranty);
		pst.setString(11,productRebates);
		
		pst.executeUpdate();
		try{
			if (!prod.isEmpty())
			{
				String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
				"VALUES (?,?);";
				PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
				pst1.setString(1,prod);
				pst1.setString(2,productId);
				pst1.executeUpdate();
				
			}
		}catch(Exception e)
		{
			msg = "Erro while adding the product";
			e.printStackTrace();
			
		}
		
		
		
	}
	catch(Exception e)
	{
		msg = "Erro while adding the product";
		e.printStackTrace();
		
	}
	return msg;
}
public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount)
{ 
	String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=? where Id =?;" ;
		
		
		
		PreparedStatement pst = conn.prepareStatement(updateProductQurey);
		
		pst.setString(1,productName);
		pst.setDouble(2,productPrice);
		pst.setString(3,productImage);
		pst.setString(4,productManufacturer);
		pst.setString(5,productCondition);
		pst.setDouble(6,productDiscount);
		pst.setString(7,productId);
		pst.executeUpdate();
		
		
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
	return msg;	
}
public static String deleteproducts(String productId)
{   String msg = "Product is deleted successfully";
try
{
	
	getConnection();
	String deleteproductsQuery ="Delete from Productdetails where Id=?";
	PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
	pst.setString(1,productId);
	
	pst.executeUpdate();
}
catch(Exception e)
{
	msg = "Proudct cannot be deleted";
}
return msg;
}

public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
		
	}
}

public static void deleteTransaction(int orderId,String orderName)
{
    try
    {
        
        getConnection();
        String deleteTransactionQuery ="Delete from Transactions where OrderId=? and productId=?";
        PreparedStatement pst = conn.prepareStatement(deleteTransactionQuery);
        pst.setInt(1,orderId);
        pst.setString(2,orderName);
        pst.executeUpdate();
    }
    catch(Exception e)
    {
        
    }
}

public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo)
{
	try
	{
		
		getConnection();
		
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo) "
		+ "VALUES (?,?,?,?,?,?);";	
		
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setString(3,orderName);
		pst.setDouble(4,orderPrice);
		pst.setString(5,userAddress);
		pst.setString(6,creditCardNo);
		pst.execute();
	}
	catch(Exception e)
	{
		
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
	
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("OrderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
			

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"));
			listOrderPayment.add(order);
			
		}
		
		
	}
	catch(Exception e)
	{
		
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String usertype)
{
	try
	{	

		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
		+ "VALUES (?,?,?,?);";	
		
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
		
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
			{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
		hm.put(rs.getString("username"), user);
	}
}
catch(Exception e)
{
}
return hm;			
}


}	