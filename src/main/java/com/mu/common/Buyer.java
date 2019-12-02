package com.mu.common;

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
    private double alphaNK=new Random().nextDouble()+1;

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

    @Override
    public String toString() {
        return "Buyer{" +
                "BNK=" + BNK +
                ", alphaNK=" + alphaNK +
                ", SK=" + SK +
                '}';
    }
}
