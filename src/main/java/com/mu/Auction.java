package com.mu;

import com.mu.common.AuctionParams;

/**
 * The main class will go here
 */
public class Auction {
    public static void main(String[] args) {
        System.out.println("Hello auction!");
        var params=AuctionParams.getDefault();
        System.out.println(params);
    }
}
