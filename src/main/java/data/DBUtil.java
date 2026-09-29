package data;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Tiện ích lấy EntityManagerFactory cho persistence unit "surveyPU".
 *
 * Thông tin kết nối được đọc từ biến môi trường:
 *   DB_URL   (không bắt buộc) mặc định jdbc:postgresql://localhost:5432/survey_db
 *            Chấp nhận cả dạng JDBC (jdbc:postgresql://...) lẫn dạng URL của Render
 *            (postgresql://user:pass@host/db), code sẽ tự chuyển sang JDBC.
 *   DB_USER  (không bắt buộc) ghi đè user trong URL; mặc định postgres
 *   DB_PASS  (bắt buộc nếu URL không chứa mật khẩu) mật khẩu của user PostgreSQL
 */
public class DBUtil {

    private static EntityManagerFactory emf;

    private DBUtil() {
    }

    public static synchronized EntityManagerFactory getEmFactory() {
        if (emf == null || !emf.isOpen()) {
            emf = createFactory();
        }
        return emf;
    }

    private static EntityManagerFactory createFactory() {
        String url = getenv("DB_URL", "jdbc:postgresql://localhost:5432/survey_db");
        String user = null;
        String pass = null;

        // Nếu là URL kiểu Render: postgresql://user:pass@host[:port]/db
        if (url.startsWith("postgres://") || url.startsWith("postgresql://")) {
            try {
                URI uri = new URI(url);
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getPath();
                if (uri.getQuery() != null) {
                    jdbcUrl += "?" + uri.getQuery();
                }
                url = jdbcUrl;

                String userInfo = uri.getUserInfo();
                if (userInfo != null) {
                    String[] parts = userInfo.split(":", 2);
                    user = parts[0];
                    if (parts.length > 1) {
                        pass = parts[1];
                    }
                }
            } catch (URISyntaxException e) {
                throw new IllegalStateException("DB_URL không hợp lệ: " + e.getMessage(), e);
            }
        }

        // Biến môi trường riêng (nếu có) được ưu tiên hơn thông tin trong URL
        user = getenv("DB_USER", user != null ? user : "postgres");
        String envPass = System.getenv("DB_PASS");
        if (envPass != null && !envPass.isBlank()) {
            pass = envPass;
        }
        if (pass == null) {
            throw new IllegalStateException(
                    "Chưa cấu hình mật khẩu PostgreSQL (biến DB_PASS hoặc mật khẩu trong DB_URL)");
        }

        Map<String, String> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url", url);
        props.put("jakarta.persistence.jdbc.user", user);
        props.put("jakarta.persistence.jdbc.password", pass);

        return Persistence.createEntityManagerFactory("surveyPU", props);
    }

    private static String getenv(String name, String defaultValue) {
        String v = System.getenv(name);
        return (v == null || v.isBlank()) ? defaultValue : v;
    }

    /** Đóng factory khi ứng dụng tắt (được gọi từ DbShutdownListener). */
    public static synchronized void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}