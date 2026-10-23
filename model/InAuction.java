package model;

import java.util.concurrent.locks.ReentrantLock;

public class InAuction extends Auction {
    private Item currentItem;                // -current item
    private double price;                    // +price:double
    private int numberOfPeopleFollowing;     // +number of people following:int
    private double increasePriceStep;        // -inscrease price
    private Bidder currentHighestBidder;

    // Khóa đồng bộ phục vụ lock price / unlock price khi đấu giá qua mạng
    private final ReentrantLock priceLock = new ReentrantLock();

    public InAuction(int roomId, Item currentItem, double increasePriceStep, int timeLeft) {
        super(roomId, timeLeft);
        this.currentItem = currentItem;
        this.price = (currentItem != null) ? currentItem.getDesiredPrice() : 0.0;
        this.increasePriceStep = increasePriceStep;
        this.numberOfPeopleFollowing = 0;
        if (currentItem != null) {
            addItem(currentItem);
        }
        setOnline(true);
    }

    @Override
    public void addParticipant(User user) {
        super.addParticipant(user);
        this.numberOfPeopleFollowing = getPeople().size();
    }

    // 1. receive request
    public boolean receiveRequest(Bidder bidder, double newBidPrice) {
        if (!isOnline() || getTimeLeft() <= 0 || bidder == null) {
            return false;
        }
        lockPrice();
        try {
            if (newBidPrice >= this.price + this.increasePriceStep) {
                increasePrice(newBidPrice);
                // Cập nhật trạng thái LastManStanding cho người trả giá cao nhất
                if (currentHighestBidder != null && currentHighestBidder != bidder) {
                    currentHighestBidder.setLastManStanding(false);
                }
                currentHighestBidder = bidder;
                bidder.setLastManStanding(true);
                showNewPrice();
                return true;
            } else {
                System.out.printf("[Phòng %d] Từ chối giá %.2f từ %s (Giá tối thiểu hợp lệ: %.2f)%n",
                        getRoomId(), newBidPrice, bidder.getName(), this.price + this.increasePriceStep);
                return false;
            }
        } finally {
            unlockPrice();
        }
    }

    // 2. lock price
    private void lockPrice() {
        priceLock.lock();
    }

    // 3. inscrease price (chuẩn hóa tên: increasePrice)
    private void increasePrice(double newPrice) {
        this.price = newPrice;
    }

    // 4. unlock price
    private void unlockPrice() {
        if (priceLock.isHeldByCurrentThread()) {
            priceLock.unlock();
        }
    }

    // 5. show new price
    public void showNewPrice() {
        String leader = (currentHighestBidder != null) ? currentHighestBidder.getName() : "Chưa có";
        System.out.printf("[Phòng %d - %s] Giá mới cập nhật: %.2f | Người dẫn đầu: %s | Thời gian còn: %s%n",
                getRoomId(),
                (currentItem != null ? currentItem.getName() : "N/A"),
                this.price,
                leader,
                getFormattedTimeLeft());
    }

    // Getters & Setters
    public Item getCurrentItem() { return currentItem; }
    public double getPrice() { return price; }
    public int getNumberOfPeopleFollowing() { return numberOfPeopleFollowing; }
    public double getIncreasePriceStep() { return increasePriceStep; }
    public Bidder getCurrentHighestBidder() { return currentHighestBidder; }
}
