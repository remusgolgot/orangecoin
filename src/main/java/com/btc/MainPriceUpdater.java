package com.btc;

import com.btc.database.Query;
import com.btc.model.Price;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;

public class MainPriceUpdater {

    public static void main(String[] args) {
        BufferedReader reader;
        System.out.println("Updating Prices Table ...");
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\sumer\\code\\orangecoin\\other\\btc-usd-max.csv"));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
                if (line != null) {
                    String[] lineElements = line.split(",");
                    Price price = new Price(lineElements[0],
                            Math.round(Double.parseDouble(lineElements[1]) * 100.0) / 100.0, // 2 decimals only
                            Math.round(Double.parseDouble(lineElements[2]) / 1000000 * 100.0) / 100.0, // 2 decimals only
                            Math.round(Double.parseDouble(lineElements[3]) / 1000000 * 100.0) / 100.0); // 2 decimals only
                    Query query = new Query();
                    query.insertPrice(price);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
