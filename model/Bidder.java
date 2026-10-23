package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bidder extends User implements BidderAction {
    private double maxExpenditureAmount;         // Max expenditure amount:double
    private List<AuctionRecord> auctionHistory;  // Auction history:List(name,cost)
    private double maxExpectedAmount;            // Max expected amount:double
    private boolean affordability;               // Affordability:Boolean
    private boolean lastManStanding;             // LastManStanding:Boolean
    private double currentBidAmount;

    public Bidder(int id, String name, short age, double balance, String email, String password,
                  double maxExpenditureAmount, double maxExpectedAmount) {
        super(id, name, age, User.ROLE_BIDDER, balance, email, password);
        this.maxExpenditureAmount = maxExpenditureAmount;
        this.maxExpectedAmount = maxExpectedAmount;
        this.auctionHistory = new ArrayList<>();
        this.affordability = balance >= maxExpectedAmount;
        this.lastManStanding = false;
        this.currentBidAmount = 0.0;
    }

    @Override
    public String getRoleDescription() {
        return "Bidder (Người đấu giá)";
    }

    // Cài đặt các hành vi trong khối Action
    @Override
    public double setBid(double amount) {
        if (amount > 0 && amount <= getBalance() && amount <= maxExpenditureAmount) {
            this.currentBidAmount = amount;
            this.affordability = true;
        } else {
            this.affordability = false;
        }
        return this.currentBidAmount;
    }

    @Override
    public boolean bidIncrease(InAuction auction, double amount) {
        if (getCurrentStatus() == STATUS_BANNED || auction == null) {
            return false;
        }
        double newBid = auction.getPrice() + amount;
        // Kiểm tra khả năng chi trả trước khi gửi yêu cầu lên phiên đấu giá
        if (newBid > getBalance() || newBid > maxExpenditureAmount) {
            this.affordability = false;
            return false;
        }
        this.affordability = true;
        return auction.receiveRequest(this, newBid);
    }

    @Override
    public double autoBid(InAuction auction, double step) {
        if (auction == null || step <= 0) return auction != null ? auction.getPrice() : -1;
        double targetBid = auction.getPrice() + step;
        // Tự động tăng giá nếu vẫn nằm trong ngưỡng kỳ vọng (maxExpectedAmount)
        if (targetBid <= maxExpectedAmount && bidIncrease(auction, step)) {
            return auction.getPrice();
        }
        return -1; // Trả về -1 nếu vượt ngưỡng auto-bid
    }

    @Override
    public List<AuctionRecord> viewHistory() {
        return Collections.unmodifiableList(auctionHistory);
    }

    public void addHistoryRecord(String itemName, double cost) {
        this.auctionHistory.add(new AuctionRecord(itemName, cost));
    }

    // Getters & Setters
    public double getMaxExpenditureAmount() { return maxExpenditureAmount; }
    public void setMaxExpenditureAmount(double maxExpenditureAmount) {
        this.maxExpenditureAmount = maxExpenditureAmount;
    }

    public double getMaxExpectedAmount() { return maxExpectedAmount; }
    public void setMaxExpectedAmount(double maxExpectedAmount) {
        this.maxExpectedAmount = maxExpectedAmount;
    }

    public boolean isAffordability() {
        this.affordability = getBalance() >= maxExpectedAmount;
        return affordability;
    }

    public boolean isLastManStanding() { return lastManStanding; }
    public void setLastManStanding(boolean lastManStanding) {
        this.lastManStanding = lastManStanding;
    }
}
