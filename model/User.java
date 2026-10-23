package model;

public abstract class User {
    // Các hằng số quy ước Role và Status (kiểu short theo đúng Class Diagram)
    public static final short ROLE_ADMIN = 0;
    public static final short ROLE_SELLER = 1;
    public static final short ROLE_BIDDER = 2;

    public static final short STATUS_OFFLINE = 0;
    public static final short STATUS_ONLINE = 1;
    public static final short STATUS_BANNED = -1;

    // Thuộc tính private đảm bảo tính Đóng gói (Encapsulation)
    private String name;
    private short age;
    private int id;
    private short role;
    private double balance;
    private short currentStatus;
    private String email;
    private String password;

    public User(int id, String name, short age, short role, double balance, String email, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.role = role;
        this.balance = Math.max(0, balance);
        this.currentStatus = STATUS_ONLINE;
        this.email = email;
        this.password = password;
    }

    // Phương thức trừu tượng bắt buộc các lớp con ghi đè (Tính Đa hình)
    public abstract String getRoleDescription();

    // Phương thức xem hồ sơ có thể được ghi đè ở từng lớp con (Tính Đa hình)
    public String viewProfile(User anyone) {
        if (anyone == null) return "Người dùng không tồn tại.";
        return String.format("Hồ sơ công khai -> ID: %d | Tên: %s | Vai trò: %s",
                anyone.getId(), anyone.getName(), anyone.getRoleDescription());
    }

    // Hệ thống Getter / Setter kiểm soát dữ liệu
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public short getAge() { return age; }
    public void setAge(short age) {
        if (age > 0) this.age = age;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public short getRole() { return role; }
    protected void setRole(short role) { this.role = role; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) {
        if (balance >= 0) this.balance = balance;
    }

    public short getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(short currentStatus) { this.currentStatus = currentStatus; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Bảo mật mật khẩu: Không tạo getPassword() công khai, chỉ hỗ trợ kiểm tra và đổi mật khẩu
    public boolean verifyPassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

    public void setPassword(String password) {
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
    }
}
