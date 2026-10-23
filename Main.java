import manager.AuctionManager;
import model.*;

public class Main {
    public static void main(String[] args) {
        // 1. Kiểm tra Singleton của AuctionManager (Nhiệm vụ 3)
        AuctionManager manager1 = AuctionManager.getInstance();
        AuctionManager manager2 = AuctionManager.getInstance();
        System.out.println("Kiểm tra Singleton (manager1 == manager2): " + (manager1 == manager2));

        // 2. Khởi tạo các lớp kế thừa từ User (Nhiệm vụ 2)
        Admin admin = new Admin(1, "Trường Admin", (short) 20, 0.0, "admin@uet.vnu.edu.vn", "admin123");
        Seller seller = new Seller(2, "Minh Seller", (short) 22, 500.0, "seller@gmail.com", "seller123");
        Bidder bidder1 = new Bidder(3, "An Bidder", (short) 21, 5000.0, "an@gmail.com", "pass1", 4000.0, 3500.0);
        Bidder bidder2 = new Bidder(4, "Bình Bidder", (short) 23, 6000.0, "binh@gmail.com", "pass2", 5500.0, 5000.0);

        manager1.registerUser(admin);
        manager1.registerUser(seller);
        manager1.registerUser(bidder1);
        manager1.registerUser(bidder2);

        // 3. Kiểm tra tính Đa hình (Polymorphism) với phương thức viewProfile()
        System.out.println("\n--- KIỂM TRA ĐA HÌNH (POLYMORPHISM) ---");
        System.out.println(bidder1.viewProfile(seller)); // Góc nhìn của người dùng thường
        System.out.println(admin.viewProfile(seller));   // Góc nhìn của Admin (ghi đè phương thức)

        // 4. Seller đăng bán vật phẩm và Admin cấu hình hệ thống
        System.out.println("\n--- KHỞI TẠO PHIÊN ĐẤU GIÁ ---");
        admin.setMaxAuctionRooms(5);
        Item camera = new Item("Canon EOS R Body", 2000.0);
        seller.listItemForSale(camera);
        seller.setDesiredPrice(camera, 2200.0); // Cập nhật giá mong muốn

        // Tạo 1 phòng đang diễn ra (InAuction) và 1 phòng sắp mở (Auction)
        InAuction activeRoom = new InAuction(101, camera, 100.0, 1800);
        activeRoom.addParticipant(bidder1);
        activeRoom.addParticipant(bidder2);
        admin.setTimeLeftForAuction(activeRoom, 3600); // Admin đặt lại thời gian 1 tiếng (01:00:00)

        Auction upcomingRoom = new Auction(102, 7200); // Phòng chờ chưa online

        manager1.addAuctionRoom(activeRoom);
        manager1.addAuctionRoom(upcomingRoom);

        // 5. Mô phỏng luồng đấu giá (Action & InAuction)
        System.out.println("\n--- DIỄN BIẾN ĐẤU GIÁ ---");
        bidder1.bidIncrease(activeRoom, 150.0); // Tăng 150 -> Giá lên 2350
        bidder2.autoBid(activeRoom, 200.0);     // Auto-bid tăng 200 -> Giá lên 2550
        bidder1.bidIncrease(activeRoom, 50.0);  // Bước giá tối thiểu là 100 -> Sẽ bị từ chối

        // 6. Hiển thị trạng thái tổng thể của AuctionManager
        System.out.println();
        manager1.printSystemStatus();
    }
}
