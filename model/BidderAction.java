package model;

import java.util.List;

public interface BidderAction {
    boolean bidIncrease(InAuction auction, double amount); // bid increase():return(True,False)
    List<AuctionRecord> viewHistory();                     // ViewHistory():List
    String viewProfile(User anyone);                       // ViewProfile(anyone)
    double setBid(double amount);                          // setBid()-> double
    double autoBid(InAuction auction, double step);        // AutoBin()-> double
}
