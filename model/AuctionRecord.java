package model;

// Lớp lưu lịch sử đấu giá của Bidder: List(name, cost)
public class AuctionRecord {
    private String itemName;
    private double cost;

    public AuctionRecord(String itemName, double cost) {
        this.itemName = itemName;
        this.cost = cost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (cost >= 0) {
            this.cost = cost;
        }
    }

    @Override
    public String toString() {
        return String.format("[Vật phẩm: %s | Giá chốt: %.2f]", itemName, cost);
    }
}
