package download;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import object.User;
import util.CookieUtil;
import util.UserIO;

import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // code xử lý
        String action = request.getParameter("action");
        if(action == null){ action = "viewAlbums";  }

        String url = "/index.jsp";
        if(action.equals("viewAlbums")){  url = "/index.jsp"; }
        else if (action.equals("checkUser")){   url = checkUser(request, response); }
        else if (action.equals("logout")) { url = logout(request, response); }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        String url = "/index.jsp";
        if(action.equals("registerUser")){
            url = registerUser(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String checkUser(HttpServletRequest request, HttpServletResponse response){
        String productCode = request.getParameter("productCode");
        HttpSession session = request.getSession();
        session.setAttribute("productCode", productCode);

        User user = (User) session.getAttribute("user");

        String url = "";

        if(user == null){
            Cookie[] cookies = request.getCookies();
            String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");

            if(emailAddress == null || emailAddress.isEmpty()){
                url = "/register.jsp";
            }
            else{
                ServletContext sc = getServletContext();
                String path = sc.getRealPath("/WEB-INF/EmailList.txt");
                user = UserIO.getUser(emailAddress, path);
                session.setAttribute("user", user);
                url = "/" + productCode + ".jsp";
            }
        }
        else { url = "/" + productCode + ".jsp"; }
        return url;
    }

    private String registerUser(HttpServletRequest request, HttpServletResponse response){
        //get User data
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        //store data in User object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        //write User object to file
        ServletContext sc = getServletContext();
        String path = sc.getRealPath("/WEB-INF/EmailList.txt");
        UserIO.add(user, path);

        //store User object as a session attribute
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        //add cookie stores user's email to browser
        Cookie c = new Cookie("emailCookie", email);
        c.setMaxAge(60 * 60 * 24 * 365 * 2);
        c.setPath("/");
        response.addCookie(c);

        //create a return URL for the appropriate Download page
        String productCode = (String) session.getAttribute("productCode");
        String url = "/" + productCode + ".jsp";
        return url;
    }

    private String logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.invalidate();

        Cookie c = new Cookie("emailCookie", "");
        c.setMaxAge(0);
        c.setPath("/");
        response.addCookie(c);

        return "/index.jsp";
    }
}
