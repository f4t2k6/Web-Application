package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<LineItem> items = new ArrayList<>();

    public Cart(){

    }

    public List<LineItem> getItems(){
        return this.items;
    }
    public void setItems(List<LineItem> items){
        this.items = items;
    }

    // Custom methods
    public void addLineItem(LineItem newItem){
        for (LineItem item : items) {
            if (item.getProduct().getProductID().equals(newItem.getProduct().getProductID())) {

                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        // Sản phẩm chưa có → thêm mới
        items.add(newItem);
    }

    public void removeLineItem(String ProductID){
        items.removeIf(items -> items.getProduct().getProductID().equals(ProductID));
    }

    public void updateLineItem(String ProductID, int quantity){
        if(quantity<=0){
            removeLineItem(ProductID);
            return;
        }

        for(LineItem item : items){
            if(item.getProduct().getProductID().equals(ProductID)){
                item.setQuantity(quantity);
            }
        }
    }

//    public double GetTotalCartPrice(){
//        double total = 0;
//        for (LineItem item : items){
//            total += item.GetTotalLineItemPrice();
//        }
//        return total;
//    }
}
