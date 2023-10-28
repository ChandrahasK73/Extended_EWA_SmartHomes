import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInventory {
    private static final String JSON_FILE_PATH = "C:\\apache-tomcat-9.0.52\\webapps\\Extended_EWA_SmartHomes\\js\\productInventory.json";
    private static final String JSON_SALE_PATH = "C:\\apache-tomcat-9.0.52\\webapps\\Extended_EWA_SmartHomes\\js\\totalSales.json";

    public static List<ProductCount> loadInventory() throws IOException {
        List<ProductCount> products = new ArrayList<>();
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray productArray = jsonObject.getAsJsonArray("products");
            for (JsonElement element : productArray) {
                JsonObject productObject = element.getAsJsonObject();
                String name = productObject.get("name").getAsString();
                double price = productObject.get("price").getAsDouble();
                int quantity = productObject.get("quantity").getAsInt();
                int totalSold = productObject.get("totalSold").getAsInt();
                double totalSales = productObject.get("totalSales").getAsDouble();
                String manufacturerRebate = productObject.get("manufacturerRebate").getAsString();
                String onSale = productObject.get("onSale").getAsString();
                products.add(new ProductCount(name, price, quantity, totalSold, totalSales, manufacturerRebate, onSale));
            }
        }
        return products;
    }

    public static void saveInventory(List<ProductCount> products) throws IOException {
        JsonArray productArray = new JsonArray();
        for (ProductCount product : products) {
            JsonObject productObject = new JsonObject();
            productObject.addProperty("name", product.getName());
            productObject.addProperty("price", product.getPrice());
            productObject.addProperty("quantity", product.getQuantity());
            productObject.addProperty("totalSold", product.getTotalSold());
            productObject.addProperty("totalSales", product.getTotalSales());
            productObject.addProperty("manufacturerRebate", product.getManufacturerRebate());
            productObject.addProperty("onSale", product.getOnSale());
            productArray.add(productObject);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("products", productArray);

        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
        }
    }

    public static List<SalesCount> loadSales() throws IOException {
        List<SalesCount> sales = new ArrayList<>();
        try (Reader reader = new FileReader(JSON_SALE_PATH)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray salesArray = jsonObject.getAsJsonArray("sales");
            for (JsonElement element : salesArray) {
                JsonObject salesObject = element.getAsJsonObject();
                String date = salesObject.get("date").getAsString();
                double totalSales = salesObject.get("totalSales").getAsDouble();
                sales.add(new SalesCount(date, totalSales));
            }
        }
        return sales;
    }

    public static void saveSales(List<SalesCount> sales) throws IOException {
        JsonArray salesArray = new JsonArray();
        for (SalesCount sale : sales) {
            JsonObject salesObject = new JsonObject();
            salesObject.addProperty("date", sale.getDate());
            salesObject.addProperty("totalSales", sale.getTotalSales());
            salesArray.add(salesObject);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("sales", salesArray);

        try (Writer writer = new FileWriter(JSON_SALE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
        }
    }

    
}
