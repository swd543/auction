package com.mu.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Buyer {
    /**
     * The bidding price offered by this buyer
     */
    private double BNK;
    private double profit;
    private ArrayList<ArrayList<Double>> won_bids;

    /**
     * The bidding factor for this buyer, unique alpha value >= 1
     * TODO: initially needs to be randomized, then after each auction round needs to be updated with new alpha values
     * TODO: The line below generates randoms between 1 and 2, should it be more?
     */
    private double alphaNK=new Random().nextDouble()+1;

    /**
     * The quoted selling price offered by the seller
     */
    private double SK;

    public Buyer(double alphaNK, double SK) {
        this.alphaNK = alphaNK;
        this.SK = SK;
        this.profit = 0;
        this.won_bids = new ArrayList<ArrayList<Double>>();
    }

    public Buyer(double SK) {
        this.SK = SK;
    }

    public Buyer(){}

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

    public double bid(){
        this.BNK=this.alphaNK*this.SK;
        return this.BNK;
    }

    public void addWonBid(double avg, double bid){
        won_bids.add(new ArrayList<Double>(Arrays.asList(avg,bid)));
    }

    public void calculateProfit(){
        for (int i = 0; i < won_bids.size(); i++){
            for (int j = 0; j < won_bids.get(i).size(); j++){
                profit += won_bids.get(i).get(j);
            }
        }
    }

    public double getProfit(){
        return profit;
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
