package util;

import java.util.Properties;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Helper gửi mail qua SMTP server từ xa (Gmail, smtps, cổng 465, có đăng nhập).
 *
 * Tài khoản và mật khẩu KHÔNG viết cứng trong code. Đặt 2 biến môi trường:
 *   GMAIL_USER      = địa chỉ Gmail của bạn
 *   GMAIL_APP_PASS  = App Password 16 ký tự của Google (không phải mật khẩu đăng nhập)
 */
public class MailUtilGmail {

    public static void sendMail(String to, String from,
                                String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        String user = System.getenv("GMAIL_USER");
        String password = System.getenv("GMAIL_APP_PASS");
        if (user == null || password == null) {
            throw new MessagingException(
                    "Chưa cấu hình biến môi trường GMAIL_USER / GMAIL_APP_PASS");
        }

        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", 465);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        // 2 - create a message
        MimeMessage  message = new MimeMessage(session);
        message.setSubject(subject, "UTF-8");           // hỗ trợ tiếng Việt có dấu
        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body, "UTF-8");
        }

        // 3 - address the message
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 4 - send the message (server cần đăng nhập nên dùng connect/sendMessage/close)
        Transport transport = session.getTransport();
        try {
            transport.connect(user, password);
            transport.sendMessage(message, message.getAllRecipients());
        } finally {
            transport.close();
        }
    }
}