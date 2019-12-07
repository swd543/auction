package com.mu.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Buyer {
    /**
     * The bidding price offered by this buyer
     */
    private double BNK;

    /**
     * The bidding factor for this buyer, unique alpha value >= 1
     * TODO: initially needs to be randomized, then after each auction round needs to be updated with new alpha values
     * TODO: The line below generates randoms between 1 and 2, should it be more?
     */
    private static int identifier = 1;
    private double alphaNK=new Random().nextDouble()+1;
    private double decreaseFactor= new Random().nextDouble();   // must be lower than or equal to 1
    private double increaseFactor= new Random().nextDouble()+1; // must bre greater than or equal to 1
    private double profit = 0;
    private int number;  // unique integer for an instance
    private double[] prevBids; // array of previous bids  for all sellers

    /**
     * The quoted selling price offered by the seller
     */
    private double SK;

    public Buyer(double alphaNK, double SK) {
        this.alphaNK = alphaNK;
        this.SK = SK;
    }

    public Buyer(double SK) {
        this.SK = SK;
    }

    public Buyer(int n){
        this.number = identifier;
        identifier++;
        prevBids = new double[n];
    }

    public double getBNK() {
        return BNK;
    }

    public Buyer setBNK(double BNK) {
        this.BNK = BNK;
        return this;
    }

    public double getAlphaNK() {
        return alphaNK;
    }

    public Buyer setAlphaNK(double alphaNK) {
        this.alphaNK = alphaNK;
        return this;
    }

    public double getSK() {
        return SK;
    }

    public Buyer setSK(double SK) {
        this.SK = SK;
        return this;
    }

    public double bid(Seller seller){
        if (seller.getAuctions().size() != 0) {
            var num = seller.getAuctions().size() - 1;
            // Adapt bids to the market prices across rounds
            if (seller.getWinners().get(num) == this || this.prevBids[seller.getNumber() - 1] >= seller.getAuctions().get(num).get(0))
                this.alphaNK -= decreaseFactor;
            else
                this.alphaNK += increaseFactor;
        }
        this.BNK=this.alphaNK*this.SK;
        prevBids[seller.getNumber()-1] = this.BNK;
        return this.BNK;
    }

    public void calculateProfit(double avg, double bid){
        this.profit += (avg-bid);
    }

    public double getProfit(){
        return this.profit;
    }

    public int getNumber(){
        return this.number;
    }
    @Override
    public String toString() {
        return "Buyer{" +
                "BNK=" + BNK +
                ", alphaNK=" + alphaNK +
                ", SK=" + SK +
                '}';
    }
}
