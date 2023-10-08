import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Store")

/* 
	Game class contains class variables name,price,image,retailer,condition,discount.

	Game class has a constructor with Arguments name,price,image,retailer,condition,discount.
	  
	Game class contains getters and setters for name,price,image,retailer,condition,discount.

*/

public class Store extends HttpServlet{
	private String id;
	private String name;
	private String street;
	private String city;
	private String state;
	private String zipcode;
	
	

    public Store(String name, String street, String city,String state,String zipcode){
		this.name=name;
		this.street=street;
		this.city=city;
		this.state=state;
		this.zipcode = zipcode;
	}
	
	public Store(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
		

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
}
