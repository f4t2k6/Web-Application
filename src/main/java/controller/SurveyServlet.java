package controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;

import data.UserDB;
import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import util.MailUtilBrevo;

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

        // Truy cập trực tiếp (GET) hoặc thiếu dữ liệu bắt buộc thì quay về form
        if (fullName == null || fullName.isBlank() || email == null || email.isBlank()) {
            response.sendRedirect("index.jsp");
            return;
        }
        fullName = fullName.trim();
        email = email.trim();

        // Gộp ngày sinh lại thành 1 chuỗi
        String birthDate = birthMonth + "/" + birthDay + "/" + birthYear;

        // Chuyển sang LocalDate để lưu vào cột DATE (null nếu ngày không hợp lệ, vd 31/2 hoặc bỏ trống năm)
        LocalDate birthLocalDate = null;
        try {
            birthLocalDate = LocalDate.of(Integer.parseInt(birthYear),
                    Integer.parseInt(birthMonth), Integer.parseInt(birthDay));
        } catch (NumberFormatException | DateTimeException e) {
            // giữ nguyên null
        }

        HttpSession session = request.getSession();

        session.setAttribute("fullName", fullName);
        session.setAttribute("email", email);
        session.setAttribute("birthDate", birthDate);
        session.setAttribute("hearAbout", hearAbout);
        session.setAttribute("receiveAnnouncements", receiveAnnouncements);

        // ===== Lưu thông tin vào PostgreSQL =====
        try {
            User existing = UserDB.selectUser(email);
            if (existing == null) {
                User user = new User(fullName, email, birthLocalDate, hearAbout,
                        "Yes".equals(receiveAnnouncements));
                UserDB.insert(user);
                session.setAttribute("dbMessage", "Đã lưu thông tin vào cơ sở dữ liệu.");
            } else {
                // Email đã tồn tại: cập nhật lại thông tin thay vì thêm dòng mới
                existing.setFullName(fullName);
                existing.setBirthDate(birthLocalDate);
                existing.setHearAbout(hearAbout);
                existing.setReceiveAnnouncements("Yes".equals(receiveAnnouncements));
                UserDB.update(existing);
                session.setAttribute("dbMessage",
                        "Email này đã có trong hệ thống, thông tin đã được cập nhật.");
            }
        } catch (RuntimeException e) {
            session.setAttribute("dbMessage",
                    "ERROR: Không lưu được vào cơ sở dữ liệu. Chi tiết lỗi: " + e.getMessage());
            this.log("Unable to save user to database", e);
        }

        // ===== Gửi email xác nhận cho user =====
        String to = email;
        String from = System.getenv("BREVO_SENDER_EMAIL");
        String subject = "Cảm ơn bạn đã tham gia khảo sát";
        String body = "Xin chào " + fullName + ",\n\n"
                + "Cảm ơn bạn đã hoàn thành khảo sát của chúng tôi.\n"
                + "Thông tin bạn đã gửi:\n"
                + "- Ngày sinh: " + birthDate + "\n"
                + "- Biết đến chúng tôi qua: " + hearAbout + "\n"
                + "- Nhận thông báo: " + receiveAnnouncements + "\n\n"
                + "Chúc bạn một ngày tốt lành!\n";
        boolean isBodyHTML = false;

        try {
            MailUtilBrevo.sendMail(to, from, subject, body, isBodyHTML);
            // MailUtilLocal.sendMail(to, from, subject, body, isBodyHTML);
            session.setAttribute("emailMessage",
                    "Đã gửi email xác nhận đến " + email);
        } catch (MessagingException e) {
            String errorMessage = "ERROR: Không gửi được email. "
                    + "Xem log của Tomcat để biết chi tiết. "
                    + "Chi tiết lỗi: " + e.getMessage();
            session.setAttribute("emailMessage", errorMessage);
            this.log("Unable to send email.\n"
                    + "TO: " + to + "\n"
                    + "FROM: " + from + "\n"
                    + "SUBJECT: " + subject + "\n\n"
                    + body + "\n", e);
        }

        // Chuyển (forward) sang result.jsp để xác nhận thành công
        response.sendRedirect("result.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu ai đó truy cập trực tiếp bằng GET thì xử lý giống POST
        doPost(request, response);
    }
}