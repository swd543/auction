package com.mu.common;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Seller {
    private final AuctionParams params;
    /**
     * The selling price being set by this seller
     */
    private double SK;
    private ArrayList<ArrayList<Double>> auctions = new ArrayList<>(); // both average price nad winning bids are stored for each round
    private ArrayList<Buyer> winners = new ArrayList<>(); // buyers who won the auctions
    private double profit = 0;
    private static int identifier = 1;
    private int number;

    public Seller(AuctionParams params) {
        this.params = params;
        // Set the selling price random from 0 to SMax
        this.number = identifier;
        identifier++;
    }

    /**
     * @return the selling price
     */
    public void setSK(){
        this.SK = new Random().nextDouble()*(params.getSMax()+1);
    }
    public double getSK() {
        return SK;
    }

    // after an auctions end, the bid is paid to the seller
    public void calculateProfit(double avg, double bid, Buyer buyer){
        auctions.add(new ArrayList<>(Arrays.asList(avg,bid)));
        winners.add(buyer);
        profit += bid;
    }

    public int getNumber(){
        return number;
    }

    public double getProfit(){
        return profit;
    }
   public ArrayList<ArrayList<Double>> getAuctions(){
        return auctions;
   }
    public ArrayList<Buyer> getWinners(){
        return winners;
    }
    @Override
    public String toString() {
        return "Seller{" +
                "SK(selling price)=" + SK +
                '}';
    }
}
