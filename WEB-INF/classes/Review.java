import java.io.*;


/* 
    Review class contains class variables username,productname,reviewtext,reviewdate,reviewrating

    Review class has a constructor with Arguments username,productname,reviewtext,reviewdate,reviewrating
      
    Review class contains getters and setters for username,productname,reviewtext,reviewdate,reviewrating
*/

public class Review implements Serializable{
    private String productname;
    private String producttype;
    private String productprice;
    private String productmaker;
    private String productrebates;
    private String productOnSale;
    private String userId;
    private String userAge;
    private String userGender;
    private String userOccupation;
    private String storeId;
    private String retailerCity;
    private String retailerState;
    private String retailerPin;
    private String reviewrating;
    private String reviewdate;
    private String reviewtext;
    
    public Review (String productname,String producttype,String productprice, String productmaker, String productrebates, String productOnSale,String userId,String userAge,String userGender,String userOccupation,String storeId,String retailerCity,String retailerState,String retailerPin, String reviewrating,String reviewdate,String reviewtext){
        this.productname=productname;
        this.producttype=producttype;
        this.productprice=productprice;
        this.productmaker=productmaker;
        this.productrebates=productrebates;
        this.productOnSale=productOnSale;
        this.userId=userId;
        this.userAge= userAge;
        this.userGender= userGender;
        this.userOccupation=userOccupation;
        this.storeId= storeId;
        this.retailerCity= retailerCity;
        this.retailerState=retailerState;
        this.retailerPin= retailerPin;
        this.reviewrating= reviewrating;
        this.reviewdate= reviewdate;
        this.reviewtext= reviewtext;
    }

    // public Review(String productName, String retailerPin, String reviewRating, String reviewText) {
    //    this.productName = productName;
    //    this.retailerPin = retailerPin;
    //    this.reviewRating = reviewRating;
    //    this.reviewText = reviewText;
    // }

    public String getProductName() {
        return productname;
    }
    // public String getUserName() {
    //  return userName;
    // }

    public void setProductName(String productname) {
        this.productname = productname;
    }

    public String getProductType() {
        return producttype;
    }

    public void setProductType(String producttype) {
        this.producttype = producttype;
    }

    public String getProductMaker() {
        return productmaker;
    }

    public void setProductMaker(String productmaker) {
        this.productmaker = productmaker;
    }
    public String getProductRebates() {
        return productrebates;
    }

    public void setProductRebates(String productrebates) {
        this.productrebates = productrebates;
    }
    public String getProductOnSale() {
        return productOnSale;
    }

    public void setProductOnSale(String productOnSale) {
        this.productOnSale = productOnSale;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
    public String getUserAge() {
        return userAge;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    public String getUserGender() {
        return userGender;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    

    public String getReviewRating() {
        return reviewrating;
    }
    public void setReviewRating(String reviewrating) {
        this.reviewrating = reviewrating;
    }
    public String getReviewText() {
        return reviewtext;
    }
    public void setReviewText(String reviewtext) {
        this.reviewtext = reviewtext;
    }
    // public void setUserName(String userName) {
    //  this.userName = userName;
    // }

    
    public String getReviewDate() {
        return reviewdate;
    }

    public void setReviewDate(String reviewdate) {
        this.reviewdate = reviewdate;
    }
    
    public String getRetailerPin() {
        return retailerPin;
    }

    public void setRetailerPin(String retailerPin) {
        this.retailerPin = retailerPin;
    }
    public String getRetailerCity() {
        return retailerCity;
    }

    public void setRetailerCity(String retailerCity) {
        this.retailerCity = retailerCity;
    }
    public String getRetailerState() {
        return retailerState;
    }

    public void setRetailerState(String retailerState) {
        this.retailerState = retailerState;
    }
    
    public String getPrice() {
        return productprice;
    }

    public void setPrice(String productprice) {
        this.productprice = productprice;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

}