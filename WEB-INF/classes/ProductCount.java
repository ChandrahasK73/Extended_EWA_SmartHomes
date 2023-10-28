import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductCount")
public class ProductCount {
    private String name;
    private double price;
    private int quantity;
    private int totalSold;
    private double totalSales;
    private String manufacturerRebate;
    private String onSale;

    // Getters and setters for the above fields
    // You can generate them using your IDE or write them manually
    public ProductCount(String name, double price, int quantity, int totalSold, double totalSales, String manufacturerRebate, String onSale){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalSold = totalSold;
        this.totalSales = totalSales;
        this.manufacturerRebate = manufacturerRebate;
        this.onSale = onSale;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getManufacturerRebate(){
        return manufacturerRebate;
    }
    public void setManufacturerRebate(String manufacturerRebate){
        this.manufacturerRebate = manufacturerRebate;
    }

    public String getOnSale(){
        return onSale;
    }
    public void setOnSale(String onSale){
        this.onSale = onSale;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getTotalSold(){
        return totalSold;
    }
    public void setTotalSold(int totalSold){
        this.totalSold = totalSold;
    }

    public double getTotalSales(){
        return totalSales;
    }
    public void setTotalSales(double totalSales){
        this.totalSales = totalSales;
    }
}
