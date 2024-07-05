package deprecated;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

public class FileTotal {

    public static final String DELIMITER = ",";

    // 8845154.496518595 December 13th

    public static void main(String[] args) {
        int k =0;
        try {
            URL path = SentZero.class.getResource("addresses.txt");
            File file = new File(path.getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            double sum = 0;
            while ((st = br.readLine()) != null) {
                if (st.contains("=")) {
                    continue;
                }
                sum += Double.parseDouble(st.split(DELIMITER)[1].trim());
                k++;
            }
            System.out.println(sum);
        } catch (Exception e) {
            System.out.println("ddd");
        }
    }
}
