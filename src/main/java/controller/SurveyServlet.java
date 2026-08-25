package controller;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller nhận dữ liệu từ form Survey (index.html) và chuyển sang result.jsp.
 * Nếu project dùng Servlet 4.0 trở xuống (javax.servlet.*), đổi các import
 * "jakarta.servlet" thành "javax.servlet" cho khớp với Tomcat/thư viện đang dùng.
 */
@WebServlet("/SurveyServlet")
public class SurveyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String birthMonth = request.getParameter("birthMonth");
        String birthDay = request.getParameter("birthDay");
        String birthYear = request.getParameter("birthYear");
        String hearAbout = request.getParameter("hearAbout");
        String receiveAnnouncements = request.getParameter("receiveAnnouncements");

        // Gộp ngày sinh lại thành 1 chuỗi cho dễ hiển thị
        String birthDate = birthMonth + "/" + birthDay + "/" + birthYear;

        // Đưa dữ liệu vào request để result.jsp có thể lấy ra hiển thị
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("birthDate", birthDate);
        request.setAttribute("hearAbout", hearAbout);
        request.setAttribute("receiveAnnouncements", receiveAnnouncements);

        // Chuyển (forward) sang result.jsp để xác nhận thành công
        RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu ai đó truy cập trực tiếp bằng GET thì xử lý giống POST
        doPost(request, response);
    }
}