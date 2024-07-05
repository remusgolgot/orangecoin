package deprecated.price;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

public class MainPriceHistory {

    //
    public static void main(String[] args) {

        long sum = 0;
        long count = 200;
        try {
            URL path = MainPriceHistory.class.getResource("priceHistory.txt");
            File file = new File(path.getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            long c = 0;
            while (c < count && (st = br.readLine()) != null) {
                c++;
                sum += Long.parseLong(st.split(",")[1]);
            }
            System.out.println("Average = " + (sum / c));
            System.out.println("Count = " + c);
        } catch (Exception e) {
            //
        }
    }
}
