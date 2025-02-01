package com.btc;

import com.btc.client.BlockClient;
import com.btc.client.BlockchainClient;
import com.btc.model.Address;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.btc.processors.AddressProcessor;
import com.btc.scraping.Fetcher;
import com.btc.utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainBitInfoCharts {

    static String URL_LOCATION = "https://bitinfocharts.com/top-100-richest-bitcoin-addresses.html";
    static BlockClient blockClient = new BlockchainClient();
    static final long timeout = 2200;
    static final int NR_PAGES = 70;
    static final int delta = 67;

    public static void main(String[] args) {
        try {
            System.out.println("Fetching top addresses ...");
            Fetcher fetcher = new Fetcher();
            List<String> addresses = new ArrayList<>();
            String document = fetcher.fetchDocument(new URL(URL_LOCATION));
            if (delta == 1) {
                addresses.addAll(getAddresses(document));
            }
            for (int i = delta; i < NR_PAGES + delta; i++) {
                document = fetcher.fetchDocument(new URL(URL_LOCATION.replace(".html", "-" + i + ".html")));
                addresses.addAll(getAddresses(document));
            }
            System.out.println("addresses : " + addresses.size());
            for (String address : addresses) {
                checkAddresses(address);
            }

        } catch (Exception e) {
            //
        }
    }

    private static void checkAddresses(String addressString) {
        try {
            Address address = blockClient.callAddressAPI(addressString, timeout);
            AddressProcessor addressProcessor = new AddressProcessor();
            if (address == null) {
                System.out.println("SKIPPING " + addressString);
                return;
            }
            addressProcessor.processAddress(address.getAddress());
            if (address.isSentZero()) {
                Double balance = address.getBalance();
                System.out.println(addressString + "," + Utils.prettyBalance(balance));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<String> getAddresses(String document) {
        List<String> addresses = new ArrayList<>();
        Document doc = Jsoup.parse(document);
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String href = link.attr("href");
            if (href.contains("/bitcoin/address/")) {
                addresses.add(href.split("/")[5]);
            }
        }
        return addresses;
    }
}
