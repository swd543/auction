package com.mu;

import com.mu.common.AuctionParams;
import com.mu.common.Buyer;
import com.mu.common.Seller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The main class will go here
 */
public class Auction {
    AuctionParams params;
    ArrayList<Seller> sellers;
    ArrayList<Buyer> buyers;

    public static void main(String[] args) {
        System.out.println("Hello auction!");
        var params = AuctionParams.getDefault();
        Auction auction;
        System.out.println("Parameters set for the auction are --> " + params);

        if (!params.isLeveled())
            auction = new AuctionPure(params);
        else
            auction = new AuctionLeveled(params);
        System.out.printf("Auctioning by %s strategy.%n", auction.getClass().getSimpleName());
        auction.start();
        auction.printStatistics(auction.getBuyers(), auction.getSellers());
        // var marketPrice = buyers.stream().filter(buyer -> !winners.contains(buyer)).mapToDouble(Buyer::getBNK).average().orElse(0);
    }

    public void printStatistics(ArrayList<Buyer> buyers, ArrayList<Seller> sellers) {
        PrintWriter writer = null;
        PrintWriter writer1 = null;
        PrintWriter writer2 = null;
        try {
            // Save each seller profit
            writer = new PrintWriter("SellerProfit.csv", "UTF-8");
            // Save each seller market place
            writer1 = new PrintWriter("SellerMarket.csv", "UTF-8");
            // Save each buyer profit
            writer2 = new PrintWriter("BuyerProfit.csv", "UTF-8");


            System.out.println("*** STATISTICS ***");

            for (var seller : sellers) {
                System.out.println("Seller " + seller.getNumber() + ":");
                System.out.println("Market prices across rounds: " +
                        seller.getAuctions().stream().map(n -> n.get(0)).map(n -> (double) Math.round(n * 100) / 100).collect(Collectors.toList()));
                System.out.println("The profit equals " + ((double) Math.round(seller.getProfit() * 100) / 100) + ".");

                writer.println(+ seller.getNumber() + ", " + ((double) Math.round(seller.getProfit() * 100) / 100) );
                writer1.println(seller.getNumber() + ", " +
                        seller.getAuctions().stream().map(n -> n.get(0)).map(n -> (double) Math.round(n * 100) / 100).collect(Collectors.toList()));
            }
            for (var buyer : buyers) {
                System.out.println("Buyer " + buyer.getNumber() + ":");
                System.out.println("The profit equals " + ((double) Math.round(buyer.getProfit() * 100) / 100) + ".");

                writer2.println(buyer.getNumber() + ", " + ((double) Math.round(buyer.getProfit() * 100) / 100));
            }
            writer.close();
            writer1.close();
            writer2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void start() {
    }

    public ArrayList<Buyer> getBuyers() {
        return buyers;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }
}
