package controller;
import objects.Cart;
import objects.LineItem;
import objects.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) action = "add";

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) cart = new Cart();

        if(action.equals("add")){
            String ProductID = request.getParameter("ProductID");
            String Description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));

            Product product = new Product(ProductID, Description, price);
            cart.addLineItem(new LineItem(product, 1));
        }
        else if (action.equals("update")) {
            String ProductID = request.getParameter("ProductID");
            int Quantity = Integer.parseInt(request.getParameter("quantity"));
            cart.updateLineItem(ProductID, Quantity);
        }
        else if (action.equals("remove")){
            String ProductID = request.getParameter("ProductID");
            cart.removeLineItem(ProductID);
        }

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}
