package objects;

import java.io.Serializable;

public class Product implements Serializable {
    private String ProductID;
    private String Description;
    private double Price;

    // Khai báo một Constructor rỗng
    public Product(String ProductID, String Description, double Price){
        this.ProductID = ProductID;
        this.Description = Description;
        this.Price = Price;
    }

    //Khai báo các methods GET & SET
    public String getProductID(){
        return ProductID;
    }
    public void setProductID(String ProductID){
        this.ProductID = ProductID;
    }

    public String getDescription(){
        return Description;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }

    public double getPrice(){
        return Price;
    }
    public void setPrice(double Price){
        this.Price = Price;
    }
}
