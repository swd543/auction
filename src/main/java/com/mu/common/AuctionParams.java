package com.mu.common;

import java.util.Scanner;

public class AuctionParams {
    /**
     * In order - number of sellers, number of buyers, number of auction rounds.
     */
    private int K,N,R;

    /**
     * Universal maximum starting price
     */
    private double SMax;

    /**
     * Penalty factor
     */
    private double epsilon;

    /**
     * Pure-false, Leveled-true
     */
    private boolean isLeveled;

    private AuctionParams(){}

    public AuctionParams(int k, int n, int r, double smax, double epsilon, boolean isLeveled) {
        K = k;
        N = n;
        R = r;
        SMax = smax;
        this.epsilon = epsilon;
        this.isLeveled = isLeveled;
    }

    /**
     * @return number of sellers
     */
    public int getK() { return K; }

    /**
     * Set the number of sellers
     * @param k
     * @return AuctionParameters
     */
    public AuctionParams setK(int k) {
        K = k;
        return this;
    }

    /**
     * @return number of buyers
     */
    public int getN() { return N; }

    /**
     * Set the number of buyers
     * @param n
     * @return AuctionParameters
     */
    public AuctionParams setN(int n) {
        N = n;
        return this;
    }

    /**
     * @return number of auction rounds
     */
    public int getR() { return R; }

    public AuctionParams setR(int r) {
        R = r;
        return this;
    }

    /**
     * @return universal maximum starting price
     */
    public double getSMax() { return SMax; }

    /**
     * Set the universal maximum starting price
     * @param smax
     * @return AuctionParameters
     */
    public AuctionParams setSMax(double smax) {
        SMax = smax;
        return this;
    }

    /**
     * @return penalty factor
     */
    public double getEpsilon() { return epsilon; }

    /**
     * Set the penalty factor
     * @param epsilon
     * @return AuctionParameters
     */
    public AuctionParams setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        return this;
    }

    /**
     * @return if it is leveled
     */
    public boolean isLeveled() { return isLeveled; }

    /**
     * Set whether the auction is leveled
     * @param leveled
     * @return AuctionParameters
     */
    public AuctionParams setLeveled(boolean leveled) {
        isLeveled = leveled;
        return this;
    }

    @Override
    public String toString() {
        return "AuctionParams{" +
                "K(# sellers)=" + K +
                ", N(# buyers)=" + N +
                ", R(# auction rounds)=" + R +
                ", SMax(universal max starting price)=" + SMax +
                ", epsilon(penalty factor)=" + epsilon +
                ", isLeveled=" + isLeveled +
                '}';
    }

    public static AuctionParams getInput(){
        try(Scanner scanner=new Scanner(System.in)){
            var params=new AuctionParams();
            System.out.println("Please enter number of sellers K:");
            params.setK(scanner.nextInt());
            System.out.println("Please enter number of buyers N:");
            params.setN(scanner.nextInt());
            System.out.println("Please enter number of auction rounds R:");
            params.setR(scanner.nextInt());
            System.out.println("Please enter universal maximum starting price:");
            params.setSMax(scanner.nextDouble());
            System.out.println("Please enter penalty factor epsilon:");
            params.setEpsilon(scanner.nextDouble());
            System.out.println("Please enter whether it is a leveled auction:");
            params.setLeveled(scanner.nextBoolean());
            return params;
        }
    }

    public static AuctionParams getDefault(){
        return new AuctionParams(7, 9, 6, 100d, 0.9, false);
    }
} // return new AuctionParams(10, 100, 5, 100d, 0.9, true)
