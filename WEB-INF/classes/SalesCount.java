import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SalesCount")
public class SalesCount{
    public String date;
    public double totalSales;
    public SalesCount(String date, double totalSales){
        this.totalSales = totalSales;
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public double getTotalSales(){
        return this.totalSales;
    }
    public void setTotalSales(double totalSales){
        this.totalSales = totalSales;
    }
}