package com.btc.services;

import com.btc.database.Query;
import com.btc.model.Entity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSVExportService implements ExportService {
    @Override
    public void export(String path) {
        String sortBy = "balance DESC, address";
        try {
            FileWriter myWriter = new FileWriter(path + ".csv");
            myWriter.append("id,address,balance,received,meta,firstInput,lastOutput");
            myWriter.append("\n");
            Query query = new Query();
            int i = 0;
            int l = 0;
            while (true) {
                System.out.println("exporting on offset ... " + (100 * i));
                List<String> list = query.getAddressesWithLimitAndOffsetSorted(100, 100 * i, sortBy, false);
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
            System.out.println("Successfully wrote to the main file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void exportMeta(String path) {
        try {
            FileWriter myWriter = new FileWriter(path + ".csv");
            myWriter.append("entity, total");
            myWriter.append("\n");
            Query query = new Query();
            int l = 0;
            List<String> list = query.getMetaTagAddresses();
            List<Entity> entityList = new ArrayList<>();
            int i = 0;
            while (i < list.size()) {
                String meta = list.get(i).split(",")[1];
                Double total = Double.valueOf(list.get(i).split(",")[2]);
                i++;
                while (i < list.size() && list.get(i).split(",")[1].equals(meta)) {
                    total += Double.valueOf(list.get(i).split(",")[2]);
                    i++;
                }
                entityList.add(new Entity(meta, total));
            }
            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    return o1.getTotal() <= o2.getTotal() ? 1 : -1;
                }
            });

            for (int j = 0; j < entityList.size(); j++) {
                l++;
                String csq = l + "," + entityList.get(j).getName() + "," + entityList.get(j).getTotal();
                myWriter.append(csq);
                myWriter.append("\n");
            }

            myWriter.close();
            System.out.println("Successfully wrote to the meta file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
