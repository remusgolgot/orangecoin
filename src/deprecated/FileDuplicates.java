package deprecated;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class FileDuplicates {

    public static void main(String[] args) {
        try {
            URL path = SentZero.class.getResource("addresses.txt");
            File file = new File(path.getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int k = 0;
            Set<String> addresses = new HashSet<>();
            while ((st = br.readLine()) != null) {
                if (st.contains("=")) {
                    continue;
                }
                k++;
                if (st.contains(",")) {
                    String e = st.split(",")[0];
                    if (addresses.contains(e)) {
                        System.out.println("DUPLICATE " + e);
                    }
                    addresses.add(e);
                } else {

                    addresses.add(st);
                }
            }
            System.out.println(k);
            System.out.println(addresses.size());
        } catch (Exception e) {

        }
    }
}
