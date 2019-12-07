package com.mu;

import com.mu.common.AuctionParams;
import com.mu.common.Buyer;
import com.mu.common.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionPure extends Auction{
    List<Buyer> winners;

    public AuctionPure(AuctionParams params){
        this.params=params;
        sellers=new ArrayList<Seller>();
        buyers=new ArrayList<Buyer>();
        winners = new ArrayList<Buyer>();
        for(var i=0;i<params.getK();i++){
            sellers.add(new Seller(params));
        }

        for(var i=0;i<params.getN();i++){
            buyers.add(new Buyer(params.getK()));
        }
    }

    public void start(){
        for(var i=0; i<params.getR(); i++) {

            // the order must be random for each round
            Collections.shuffle(sellers);
            Collections.shuffle(buyers);

            for (var seller : sellers) {
                seller.setSK();
                for (var buyer : buyers) {
                    // Bid only if they have not won previous auctions
                    if (!winners.contains(buyer)) {
                        buyer.setSK(seller.getSK());
                        buyer.bid(seller);
                        // System.out.println(buyer+" bid "+buyer.getBNK());
                    }
                }
                // Average all the bids to get the market price
                var marketPrice = buyers.stream().filter(buyer -> !winners.contains(buyer)).mapToDouble(Buyer::getBNK).average().orElse(0);
                var num = buyers.stream().filter(buyer -> !winners.contains(buyer)).count();
                System.out.println("Auction finished! Market price determined is " +marketPrice+" with "+num+" buyers.");

                // Sort the buyers array, do not consider previous winners
                var sortedBuyers = buyers.stream()
                        .filter(buyer -> !winners.contains(buyer))
                        .filter(buyer -> buyer.getBNK() <= marketPrice)
                        .sorted((buyer, t1) -> {
                            if (buyer.getBNK() > t1.getBNK()) {
                                return -1;
                            } else if (buyer.getBNK() < t1.getBNK()) {
                                return 1;
                            }
                            return 0;
                        })
                        .collect(Collectors.toList());

                var winningBidder = sortedBuyers.get(0);
                double winningPrice;
                // in the case when there is only one buyer whose bid below the average price
                try {
                    winningPrice = sortedBuyers.get(1).getBNK();
                }
                catch(IndexOutOfBoundsException e) {
                    winningPrice = (sortedBuyers.get(0).getBNK() + seller.getSK()) / 2;
                }
                System.out.println("Buyer "+ winningBidder.getNumber() + " wins the round " + (i+1) + " of Seller "+seller.getNumber()+" at " + winningPrice);

                winners.add(winningBidder);
                winningBidder.calculateProfit(marketPrice, winningPrice);
                seller.calculateProfit(marketPrice, winningPrice, winningBidder);
            }
            winners.clear(); // empty winner list for the next round
        }
    }
}
