package model;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    private int roomId;
    private List<Item> items;       // Item:list
    private List<User> people;      // people:List
    private boolean online;         // online:boolean
    private int timeLeft;           // time left:(hh:mm:ss):int (tính bằng giây)

    public Auction(int roomId, int timeLeft) {
        this.roomId = roomId;
        this.items = new ArrayList<>();
        this.people = new ArrayList<>();
        this.online = false;
        this.timeLeft = Math.max(0, timeLeft);
    }

    public void addItem(Item item) {
        if (item != null) items.add(item);
    }

    public void addParticipant(User user) {
        if (user != null && !people.contains(user)) {
            people.add(user);
        }
    }

    // Định dạng thời gian còn lại sang chuẩn (hh:mm:ss) trên sơ đồ
    public String getFormattedTimeLeft() {
        int hours = timeLeft / 3600;
        int minutes = (timeLeft % 3600) / 60;
        int seconds = timeLeft % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    // Getters & Setters
    public int getRoomId() { return roomId; }
    public List<Item> getItems() { return items; }
    public List<User> getPeople() { return people; }
    public boolean isOnline() { return online; }
    public void setOnline(boolean online) { this.online = online; }
    public int getTimeLeft() { return timeLeft; }
    public void setTimeLeft(int timeLeft) { this.timeLeft = Math.max(0, timeLeft); }
}
