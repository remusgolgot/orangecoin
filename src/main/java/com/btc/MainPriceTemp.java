package com.btc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainPriceTemp {
    private static final String API_URL = "https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?vs_currency=usd&days=30";

    public static void main(String[] args) {
        String inputDate = "2024-11-11";
        fetchBitcoinPrice(inputDate);
    }

    public static void fetchBitcoinPrice(String targetDate) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            parseAndPrintPrice(response.toString(), targetDate);
        } catch (Exception e) {
            System.err.println("Error fetching Bitcoin price: " + e.getMessage());
        }
    }

    private static void parseAndPrintPrice(String jsonResponse, String targetDate) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray prices = jsonObject.getJSONArray("prices");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < prices.length(); i++) {
            JSONArray priceEntry = prices.getJSONArray(i);
            long timestamp = priceEntry.getLong(0);
            double price = priceEntry.getDouble(1);
            Date date = new Date(timestamp);
            String formattedDate = dateFormat.format(date);

            if (formattedDate.equals(targetDate)) {
                System.out.printf("Bitcoin Closing Price on %s: $%.2f%n", formattedDate, price);
                return;
            }
        }
        System.out.println("No data available for the specified date.");
    }
}
