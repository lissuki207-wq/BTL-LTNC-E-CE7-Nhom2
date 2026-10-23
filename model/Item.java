package model;

// Lớp đại diện cho vật phẩm đấu giá
public class Item {
    private String name;
    private double desiredPrice;

    public Item(String name, double desiredPrice) {
        this.name = name;
        this.desiredPrice = Math.max(0, desiredPrice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(double desiredPrice) {
        if (desiredPrice >= 0) {
            this.desiredPrice = desiredPrice;
        }
    }

    @Override
    public String toString() {
        return String.format("Item{name='%s', desiredPrice=%.2f}", name, desiredPrice);
    }
}
