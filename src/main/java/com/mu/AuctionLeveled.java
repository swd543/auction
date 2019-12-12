package com.mu;

import com.mu.common.AuctionParams;
import com.mu.common.Buyer;
import com.mu.common.SelledGood;
import com.mu.common.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionLeveled extends Auction{
    List<Buyer> winners;
    ArrayList<SelledGood> selledGoods;


    public AuctionLeveled(AuctionParams params){
        this.params=params;
        sellers=new ArrayList<Seller>();
        buyers=new ArrayList<Buyer>();
        winners = new ArrayList<Buyer>();

        selledGoods = new ArrayList<SelledGood>();

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
            selledGoods.clear();


            for (var seller : sellers) {
                seller.setSK();
                for (var buyer : buyers) {
                    buyer.setSK(seller.getSK());
                    var counter = 0;
                    var pos = 0;
                    for (int j = 0; j < selledGoods.size(); j++){
                        if (selledGoods.get(i).getNumBuyer() == buyer.getNumber()){
                            counter += 1;
                            pos = j;
                        }
                    }
                    if (counter > 0){ // if the buyer has already bought other good, he has a bid modified.
                        buyer.leveledBid(seller, selledGoods.get(pos), params.getEpsilon());
                    } else {
                        buyer.bid(seller);
                    }
                }
                // Average all the bids to get the market price
                var marketPrice = buyers.stream().filter(buyer -> !winners.contains(buyer)).mapToDouble(Buyer::getBNK).average().orElse(0);
                var num = buyers.size();
                System.out.println("Auction finished! Market price determined is " + marketPrice + " with " + num + " buyers.");

                // Sort the buyers array, account also for the winners of previous auctions
                var sortedBuyers = buyers.stream()
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
                var counter = 0;
                var pos = 0;
                for (int j = 0; j < selledGoods.size(); j++){
                    if (selledGoods.get(i).getNumBuyer() == winningBidder.getNumber()){
                        counter += 1;
                        pos = j;
                    }
                }

                double winningPrice;

                // in the case when there is only one buyer whose bid below the average price
                try {
                    winningPrice = sortedBuyers.get(1).getBNK();
                } catch (IndexOutOfBoundsException e) {
                    winningPrice = (sortedBuyers.get(0).getBNK() + seller.getSK()) / 2;
                }
                System.out.println("Buyer " + winningBidder.getNumber() + " wins the round " + (i + 1) + " of Seller " + seller.getNumber() + " at " + winningPrice);

                var oldProfit = selledGoods.get(pos).getMarketprice() - selledGoods.get(pos).getPrice() - (
                        params.getEpsilon() * winningBidder.getBNK());

                var newProfit = marketPrice - winningPrice - (params.getEpsilon() * selledGoods.get(pos).getBid());

                winners.add(winningBidder);
                winningBidder.calculateProfit(marketPrice, winningPrice - sortedBuyers.get(0).getPenalityFee());
                seller.calculateProfit(marketPrice, winningPrice + sortedBuyers.get(0).getPenalityFee(), winningBidder);
            }
            winners.clear(); // empty winner list for the next round
        }
    }
}
