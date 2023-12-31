import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ThermostatList")

public class ThermostatList extends HttpServlet {

	/* Trending Page Displays all the Tablets and their Information in Game Speed */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Thermostat> allThermostats = new HashMap<String,Thermostat> ();


		
		try{
		     allThermostats = MySqlDataStoreUtilities.getThermostats();
		}
		catch(Exception e)
		{
			
		}

	/* Checks the Tablets type whether it is microsft or apple or samsung */

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Thermostat> hm = new HashMap<String, Thermostat>();

		if (CategoryName == null)	
		{
			hm.putAll(allThermostats);
			name = "";
		} 
		else 
		{
			if(CategoryName.equals("Trane")) 
			{	
				for(Map.Entry<String,Thermostat> entry : allThermostats.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Trane"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Trane";
			} 
			else if (CategoryName.equals("Sensi"))
			{
				for(Map.Entry<String,Thermostat> entry : allThermostats.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Sensi"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Sensi";
			} 
			else if (CategoryName.equals("Lennox")) 
			{
				for(Map.Entry<String,Thermostat> entry : allThermostats.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Lennox"))
				 {
					hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Lennox";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the tablets and thermostats information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name + " thermostats</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Thermostat> entry : hm.entrySet()) {
			Thermostat thermostat = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + thermostat.getName() + "</h3>");
			pw.print("<strong>" + thermostat.getPrice() + "$</strong><ul>");
			pw.print("<strong>"+ "description: " + thermostat.getDescription() + "</strong><ul>");
            pw.print("<strong>"+ "You get a " + thermostat.getWarranty() + "</strong><ul>");
            pw.print("<strong>"+ "" + thermostat.getRebates() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/thermostats/"
					+ thermostat.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostat'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostat'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+thermostat.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostat'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
