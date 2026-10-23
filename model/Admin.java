package model;

import manager.AuctionManager;

public class Admin extends User {

    public Admin(int id, String name, short age, double balance, String email, String password) {
        super(id, name, age, User.ROLE_ADMIN, balance, email, password);
    }

    @Override
    public String getRoleDescription() {
        return "Administrator (Quản trị viên)";
    }

    // Đa hình: Admin xem được chi tiết cả Email, Số dư và Trạng thái của User khác
    @Override
    public String viewProfile(User anyone) {
        if (anyone == null) return "Người dùng không tồn tại.";
        return String.format("[ADMIN VIEW] ID: %d | Tên: %s | Email: %s | Số dư: %.2f | Trạng thái: %d | Vai trò: %s",
                anyone.getId(), anyone.getName(), anyone.getEmail(),
                anyone.getBalance(), anyone.getCurrentStatus(), anyone.getRoleDescription());
    }

    // set time left for auction-> int
    public int setTimeLeftForAuction(Auction auction, int seconds) {
        if (auction != null && seconds >= 0) {
            auction.setTimeLeft(seconds);
            return auction.getTimeLeft();
        }
        return -1;
    }

    // set max auction rooms -> int
    public int setMaxAuctionRooms(int maxRooms) {
        if (maxRooms > 0) {
            AuctionManager.getInstance().setMaxAuctionRooms(maxRooms);
            return AuctionManager.getInstance().getMaxAuctionRooms();
        }
        return -1;
    }

    // Ban anyone
    public void banAnyone(User target) {
        if (target != null && !(target instanceof Admin)) {
            target.setCurrentStatus(User.STATUS_BANNED);
            System.out.println("[Admin " + getName() + "] Đã khóa tài khoản của: " + target.getName());
        }
    }
}
