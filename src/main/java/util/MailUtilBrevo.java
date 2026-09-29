package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import jakarta.mail.MessagingException;

/**
 * Gửi mail qua Brevo HTTP API (HTTPS, cổng 443) thay cho SMTP,
 * vì Render Free chặn các cổng SMTP 25/465/587.
 *
 * Biến môi trường:
 *   BREVO_API_KEY       API key của Brevo (xkeysib-...)
 *   BREVO_SENDER_EMAIL  địa chỉ người gửi ĐÃ xác minh trong Brevo
 *   BREVO_SENDER_NAME   (không bắt buộc) tên hiển thị của người gửi
 */
public class MailUtilBrevo {

    private static final String API_URL = "https://api.brevo.com/v3/smtp/email";
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void sendMail(String to, String from,
                                String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        String apiKey = System.getenv("BREVO_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new MessagingException("Chưa cấu hình biến môi trường BREVO_API_KEY");
        }
        if (from == null || from.isBlank()) {
            throw new MessagingException("Chưa cấu hình BREVO_SENDER_EMAIL");
        }
        String senderName = System.getenv("BREVO_SENDER_NAME");
        if (senderName == null || senderName.isBlank()) {
            senderName = "Survey App";
        }

        String contentField = bodyIsHTML ? "htmlContent" : "textContent";
        String json = "{"
                + "\"sender\":{\"name\":\"" + esc(senderName) + "\",\"email\":\"" + esc(from) + "\"},"
                + "\"to\":[{\"email\":\"" + esc(to) + "\"}],"
                + "\"subject\":\"" + esc(subject) + "\","
                + "\"" + contentField + "\":\"" + esc(body) + "\""
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofSeconds(20))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response =
                    CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            int code = response.statusCode();
            if (code < 200 || code >= 300) {
                throw new MessagingException(
                        "Brevo trả về mã " + code + ": " + response.body());
            }
        } catch (IOException e) {
            throw new MessagingException("Không kết nối được tới Brevo: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException("Việc gửi mail bị gián đoạn", e);
        }
    }

    /** Escape chuỗi để nhúng an toàn vào JSON. */
    private static String esc(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }
}