import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class Stock {
    String ticker;
    String name;
    double price;

    public Stock(String ticker, String name, double price) {
        this.ticker = ticker;
        this.name = name;
        this.price = price;
    }
}

//  the User's Portfolio
class Portfolio {
    double balance;

    Map<String, Integer> holdings;

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
        this.holdings = new HashMap<>();
    }

    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.price * quantity;
        if (balance >= totalCost) {
            balance = balance - totalCost;
            // Add shares to our holdings
            holdings.put(stock.ticker, holdings.getOrDefault(stock.ticker, 0) + quantity);
            System.out.println("Successfully bought " + quantity + " shares of " + stock.ticker);
        } else {
            System.out.println("Insufficient funds! You need $" + totalCost);
        }
    }

    public void sellStock(Stock stock, int quantity) {
        int ownedShares = holdings.getOrDefault(stock.ticker, 0);
        if (ownedShares >= quantity) {
            double earnings = stock.price * quantity;
            balance = balance + earnings;
            // Remove shares from our holdings
            holdings.put(stock.ticker, ownedShares - quantity);
            System.out.println("Successfully sold " + quantity + " shares of " + stock.ticker);
        } else {
            System.out.println("You don't own enough shares to sell that many!");
        }
    }

    public void displayPortfolio() {
        System.out.println("\n--- Your Portfolio ---");
        System.out.println("Available Cash: $" + balance);
        System.out.println("Shares Owned:");
        for (String ticker : holdings.keySet()) {
            System.out.println(ticker + ": " + holdings.get(ticker) + " shares");
        }
        System.out.println("----------------------\n");
    }
}


public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        Portfolio myPortfolio = new Portfolio(10000.00);

        // Create some fake stocks for our market
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 150.00));
        market.put("GOOG", new Stock("GOOG", "Google LLC", 2800.00));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 700.00));

        System.out.println("Welcome to the CodeAlpha Stock Trading Platform!");

        while (true) {
            System.out.println("\n1. View Market Data");
            System.out.println("2. View My Portfolio");
            System.out.println("3. Buy Stock");
            System.out.println("4. Sell Stock");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("\n--- Market Data ---");
                for (Stock stock : market.values()) {
                    System.out.println(stock.ticker + " (" + stock.name + "): $" + stock.price);
                }
            } else if (choice == 2) {
                myPortfolio.displayPortfolio();
            } else if (choice == 3 || choice == 4) {
                System.out.print("Enter the stock ticker (e.g., AAPL): ");
                String ticker = scanner.next().toUpperCase();

                if (market.containsKey(ticker)) {
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    Stock selectedStock = market.get(ticker);

                    if (choice == 3) {
                        myPortfolio.buyStock(selectedStock, quantity);
                    } else {
                        myPortfolio.sellStock(selectedStock, quantity);
                    }
                } else {
                    System.out.println("Stock not found in the market!");
                }
            } else if (choice == 5) {
                System.out.println("Thanks for trading! Goodbye.");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
