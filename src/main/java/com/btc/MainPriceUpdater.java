package com.btc;

import com.btc.database.Query;
import com.btc.model.Price;

import java.io.BufferedReader;
import java.io.FileReader;

public class MainPriceUpdater {

    public static void main(String[] args) {

        BufferedReader reader;
        System.out.println("Updating Prices ...");
        Query query = new Query();
        String from = "2025-03-13";
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\sumer\\code\\orangecoin\\other\\prices.csv"));
            String line = reader.readLine();
            double previousPrice = 0.0;
            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
                if (line != null) {
                    String[] lineElements = line.split(",");
                    double priceValue = Double.parseDouble(lineElements[1]);
                    if (previousPrice == 0.0) {
                        previousPrice = priceValue;
                    }
                    Price price = new Price(lineElements[0],
                            Math.round(priceValue * 100.0) / 100.0, // 2 decimals only
                            Math.round(Double.parseDouble(lineElements[2]) / 1000000 * 100.0) / 100.0, // 2 decimals only
                            Math.round(Double.parseDouble(lineElements[3]) / 1000000 * 100.0) / 100.0,
                            (double) Math.round((priceValue - previousPrice) * 100.0) / 100.0,
                            Math.round(((priceValue / previousPrice) - 1) * 100 * 100.0) / 100.0
                    );
                    if (price.getDate().compareTo(from) >= 0) {
                        query.insertPrice(price);
                    }
                    previousPrice = priceValue;
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
