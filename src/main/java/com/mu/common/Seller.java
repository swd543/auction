package com.mu.common;

import java.util.Random;

public class Seller {
    private final AuctionParams params;
    /**
     * The selling price being set by this seller
     */
    private final double SK;

    public Seller(AuctionParams params) {
        this.params = params;
        // Set the selling price random from 0 to SMax
        this.SK=new Random().nextDouble()*(params.getSMax()+1);
    }

    /**
     * @return the selling price
     */
    public double getSK() {
        return SK;
    }
}
