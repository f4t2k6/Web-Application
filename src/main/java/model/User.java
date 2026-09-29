package model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity ánh xạ với bảng "users" trong PostgreSQL.
 * Dùng field annotations (đặt annotation trên field), JPA truy cập field trực tiếp.
 */
@Entity
@Table(name = "users")   // "user" là từ khóa của PostgreSQL nên phải đổi tên bảng
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // khớp với cột BIGSERIAL
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;          // ánh xạ sang kiểu DATE

    @Column(name = "hear_about", length = 50)
    private String hearAbout;

    @Column(name = "receive_announcements", nullable = false)
    private boolean receiveAnnouncements;

    // JPA bắt buộc phải có constructor không tham số
    public User() {
    }

    public User(String fullName, String email, LocalDate birthDate,
                String hearAbout, boolean receiveAnnouncements) {
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.hearAbout = hearAbout;
        this.receiveAnnouncements = receiveAnnouncements;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getHearAbout() { return hearAbout; }
    public void setHearAbout(String hearAbout) { this.hearAbout = hearAbout; }

    public boolean isReceiveAnnouncements() { return receiveAnnouncements; }
    public void setReceiveAnnouncements(boolean receiveAnnouncements) {
        this.receiveAnnouncements = receiveAnnouncements;
    }
}