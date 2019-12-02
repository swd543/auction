package com.mu;

import com.mu.common.AuctionParams;
import com.mu.common.Buyer;
import com.mu.common.Seller;

import java.util.ArrayList;

/**
 * The main class will go here
 */
public class Auction {
    public static void main(String[] args) {
        System.out.println("Hello auction!");
        var params=AuctionParams.getDefault();
        System.out.println("Parameters set for the auction are --> "+params);


        // Initialize buyers and sellers
        var sellers=new ArrayList<Seller>();
        var buyers=new ArrayList<Buyer>();
        for(var i=0;i<params.getK();i++){
            sellers.add(new Seller(params));
        }
        System.out.println(sellers);
        for(var i=0;i<params.getN();i++){
            buyers.add(new Buyer());
        }
        System.out.println(buyers);

        // Each buyer participates in every auction set by every seller
        for(var seller:sellers){
            for(var buyer:buyers){
                buyer.setSK(seller.getSK());
                buyer.bid();
                System.out.println(buyer+" bid "+buyer.getBNK());
            }
            // Average all the bids to get the market price
            var marketPrice=buyers.stream().mapToDouble(Buyer::getBNK).average().orElse(0);
            System.out.println("Auction finished! "+marketPrice);
        }
    }
}
