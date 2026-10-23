package manager;

import model.Auction;
import model.InAuction;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class AuctionManager {
    // 1. Biến static volatile lưu thực thể duy nhất của AuctionManager
    private static volatile AuctionManager instance;

    // 2. Các thuộc tính từ khối System trên Class Diagram
    private String version;                     // Version:String
    private int maxAuctionRooms;                // Giới hạn số phòng do Admin đặt
    private final List<User> registeredUsers;   // Quản lý Online/total:int/int
    private final List<Auction> auctionRooms;   // Quản lý phòng đang diễn ra & sắp diễn ra

    // 3. Constructor private để ngăn việc khởi tạo bằng từ khóa 'new' từ bên ngoài
    private AuctionManager() {
        this.version = "1.0.0-Beta2";
        this.maxAuctionRooms = 10;
        this.registeredUsers = new ArrayList<>();
        this.auctionRooms = new ArrayList<>();
    }

    // 4. Phương thức tĩnh lấy thực thể duy nhất (Double-Checked Locking)
    public static AuctionManager getInstance() {
        if (instance == null) {
            synchronized (AuctionManager.class) {
                if (instance == null) {
                    instance = new AuctionManager();
                }
            }
        }
        return instance;
    }

    // Quản lý người dùng
    public synchronized void registerUser(User user) {
        if (user != null && !registeredUsers.contains(user)) {
            registeredUsers.add(user);
        }
    }

    // Online/total:int/int
    public int getOnlineUsersCount() {
        return (int) registeredUsers.stream()
                .filter(u -> u.getCurrentStatus() == User.STATUS_ONLINE)
                .count();
    }

    public int getTotalUsersCount() {
        return registeredUsers.size();
    }

    // Quản lý phòng đấu giá
    public synchronized boolean addAuctionRoom(Auction auction) {
        if (auction == null || auctionRooms.size() >= maxAuctionRooms) {
            System.out.println("[AuctionManager] Không thể tạo thêm phòng (Đã đạt giới hạn tối đa: " + maxAuctionRooms + ")");
            return false;
        }
        auctionRooms.add(auction);
        return true;
    }

    // The auction is currently underway:int
    public int getUnderwayAuctionsCount() {
        return (int) auctionRooms.stream()
                .filter(a -> a instanceof InAuction && a.isOnline() && a.getTimeLeft() > 0)
                .count();
    }

    // upcoming auction room:int
    public int getUpcomingAuctionRoomsCount() {
        return (int) auctionRooms.stream()
                .filter(a -> !a.isOnline() && a.getTimeLeft() > 0)
                .count();
    }

    public void printSystemStatus() {
        System.out.println("================ TRẠNG THÁI HỆ THỐNG ================");
        System.out.println("Phiên bản (Version)          : " + version);
        System.out.println("Người dùng (Online/Total)    : " + getOnlineUsersCount() + "/" + getTotalUsersCount());
        System.out.println("Phòng đang đấu giá (Underway): " + getUnderwayAuctionsCount());
        System.out.println("Phòng sắp mở (Upcoming)      : " + getUpcomingAuctionRoomsCount());
        System.out.println("Giới hạn số phòng tối đa     : " + maxAuctionRooms);
        System.out.println("=====================================================");
    }

    // Getters & Setters
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public int getMaxAuctionRooms() { return maxAuctionRooms; }
    public void setMaxAuctionRooms(int maxAuctionRooms) {
        this.maxAuctionRooms = maxAuctionRooms;
    }

    public List<Auction> getAuctionRooms() { return auctionRooms; }
}
