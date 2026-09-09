package objects;

import java.io.Serializable;

public class LineItem implements Serializable {
    private Product product;
    private int quantity;

    // Constructor
    public LineItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    // Default GET SET methods
    public Product getProduct(){
        return this.product;
    }
    public void setProduct(Product product){
        this.product = product;
    }

    public int getQuantity(){
        return this.quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    // Custom method
    public double getTotalLineItemPrice(){
        return product.getPrice() * quantity;
    }
}
