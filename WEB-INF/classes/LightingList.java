import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LightingList")

public class LightingList extends HttpServlet {

	/* Games Page Displays all the Games and their Information in Game Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the Games type whether it is electronicArts or activision or takeTwoInteractive */
        /* Checks the Games type whether it is electronicArts or activision or takeTwoInteractive */
		HashMap<String,Lighting> allLightings = new HashMap<String,Lighting> ();


		/* Checks the Tablets type whether it is microsft or sony or nintendo */
		try{
		     allLightings = MySqlDataStoreUtilities.getLightings();
		}
		catch(Exception e)
		{
			
		}
				
		String name = null;
		String CategoryName = request.getParameter("maker");
        //System.out.println("---category"+CategoryName);
		HashMap<String, Lighting> hm = new HashMap<String, Lighting>();
		
		if(CategoryName==null)
		{
			hm.putAll(allLightings);
			name = "";
		}
		else
		{
		  if(CategoryName.equals("Philips"))
		  {
			for(Map.Entry<String,Lighting> entry : allLightings.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Philips"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Philips";
		  }
		  else if(CategoryName.equals("GE"))
		  {
			for(Map.Entry<String,Lighting> entry : allLightings.entrySet())
				{
				if(entry.getValue().getRetailer().equals("GE"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "GE";
		  }
		  else if(CategoryName.equals("Osram"))
		  {
			for(Map.Entry<String,Lighting> entry : allLightings.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Osram"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Osram";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Games and Games information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Lightings</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Lighting> entry : hm.entrySet()){
			Lighting lighting = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+lighting.getName()+"</h3>");
			pw.print("<strong>"+ "$" + lighting.getPrice() + "</strong><ul>");
			pw.print("<strong>"+ "description: " + lighting.getDescription() + "</strong><ul>");
            pw.print("<strong>"+ "You get a " + lighting.getWarranty() + "</strong><ul>");
            pw.print("<strong>"+ "" + lighting.getRebates() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/lightings/"+lighting.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lighting'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lighting'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lighting'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
