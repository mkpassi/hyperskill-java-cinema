package com.hyperskill;

import java.util.Scanner;

public class Cinema {
    static int[][] seatMap ;
    static int totalPurchase = 0;
    static int currentIncome = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        seatMap = new int[rows+1][seats+1];
        int choice = printMenu(scanner);
        int selectedRow=0, selectedSeat=0;
        while(choice==1 || choice==2 || choice==3){
            executeChoice(choice, scanner,selectedRow,selectedSeat);
            System.out.println();
            choice =printMenu(scanner);
        }
        scanner.close();
    }

    private static void executeChoice(int choice, Scanner scanner, int selectedRow, int selectedSeat) {
        if(choice==1) {
            populateAndPrintSeatMap(selectedRow, selectedSeat);
        }else if(choice ==2) {
            bookTicket(scanner);
        }else if(choice==3){
            printStats();
        }
    }

    private static int printMenu(Scanner scanner) {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return scanner.nextInt();
    }

    public static void printStats(){
        System.out.printf("\nNumber of purchased tickets: %d",totalPurchase);
        double purchasePercentage = getPurchasePercentage();
        System.out.printf("\nPercentage: %.2f%%",purchasePercentage);
        System.out.printf("\nCurrent income: $%d",currentIncome);
        System.out.printf("\nTotal income: $%d",getTotalIncome());
        System.out.println();
    }

    private static int getTotalIncome() {
        int frontRowCount = getTotalFrontRowCount();
        int rearRowCount = (seatMap.length-1) - frontRowCount;
        int seatsInEachRow = seatMap[0].length-1;
        return rearRowCount>0?frontRowCount*seatsInEachRow*10 + rearRowCount*seatsInEachRow*8:frontRowCount*seatsInEachRow*10;
    }

    private static double getPurchasePercentage() {
        int totalSeats = getTotalSeat();
        return (((double)totalPurchase)/totalSeats)*100;
    }

    public static void bookTicket(Scanner scanner){

        int selectedRow=0, selectedSeat=0;
        System.out.println("\nEnter a row number:");
        selectedRow=scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        selectedSeat=scanner.nextInt();
        try {
            if (seatMap[selectedRow][selectedSeat] == 1) {
                System.out.println("That ticket has already been purchased!");
                bookTicket(scanner);
            } else {
                seatMap[selectedRow][selectedSeat] = 1;
                totalPurchase++;
                int totalPrice = getPrice(seatMap, selectedRow);
                System.out.println("\nTicket Price: $" + totalPrice);
            }
        }catch (ArrayIndexOutOfBoundsException exception){
            System.out.println("Wrong input");
            bookTicket(scanner);
        }
    }

    private static int getPrice(int[][] seatMap, int row) {
        int frontRowCount = getTotalFrontRowCount();
        int rearRowCount = (seatMap.length-1) - frontRowCount;
        if(row > frontRowCount && row <= (frontRowCount+rearRowCount)){
            currentIncome +=8;
            return 8;
        }else if (row >0 && row <= frontRowCount){
            currentIncome +=10;
            return 10;
        }
        return 0;
    }

    public static int getTotalFrontRowCount(){
        int totalRows = seatMap.length-1;
        if(getTotalSeat() <=60){
            return totalRows;
        }else {
            return totalRows/2;
        }
    }


    private static int getTotalSeat(){
        return (seatMap.length-1) * (seatMap[0].length-1);
    }

    private static void populateAndPrintSeatMap(int selectedRow, int selectedSeat) {
        System.out.println("Cinema:");
        for(int row = 0; row< seatMap.length; row++){
            for(int col = 0; col< seatMap[row].length; col++){
                if(row==0 && col==0){
                    System.out.print(" ");
                }else if(row==0){
                    System.out.print(" "+col);
                }else if(col ==0){
                    System.out.print(row);
                }else{
                    if(seatMap[row][col] == 0)
                        System.out.print(" S");
                    else
                        System.out.print(" B");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
