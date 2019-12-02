package com.mu.common;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Seller {
    private final AuctionParams params;
    /**
     * The selling price being set by this seller
     */
    private final double SK;
    private ArrayList bids_paid;
    private double profit;

    public Seller(AuctionParams params) {
        this.params = params;
        // Set the selling price random from 0 to SMax
        this.SK=new Random().nextDouble()*(params.getSMax()+1);
        this.bids_paid = new ArrayList<Double>();
        this.profit = 0;
    }

    /**
     * @return the selling price
     */
    public double getSK() {
        return SK;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "SK(selling price)=" + SK +
                '}';
    }
    // after an auctions end, the bid is paid to the seller
    public void addBid(double bid){
        bids_paid.add(bid);
    }

    // calculate total profit
    public void calculateProfit() {
        for (var item : bids_paid) {
            profit += (double) item;
        }
    }

    public double getProfit(){
        return profit;
    }
}
