package com.btc.services;

import com.btc.database.Query;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExportService implements ExportService {
    @Override
    public void export(String path) {
        String sortBy = "lastUpdate";
        try {
            FileWriter myWriter = new FileWriter(path + ".csv");
            myWriter.append("id,address,balance,received,meta,lastUpdate");
            myWriter.append("\n");
            Query query = new Query();
            int i = 0;
            int l = 0;
            while (true) {
                List<String> list = query.getAddressesWithLimitAndOffsetSorted(100, 100 * i, sortBy);
                if (list.isEmpty()) {
                    break;
                }
                for (int j = 0; j < list.size(); j++) {
                    l++;
                    String csq = l + "," + list.get(j);
                    myWriter.append(csq);
                    myWriter.append("\n");
                }
                i++;
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
