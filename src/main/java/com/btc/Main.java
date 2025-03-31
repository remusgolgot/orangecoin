package com.btc;

import com.btc.processors.AddressProcessor;
import com.btc.services.CSVExportService;
import com.btc.services.ExportService;

public class Main {

    public static void main(String[] args) {

//        AddressProcessor addressProcessor = new AddressProcessor();
//
//        addressProcessor.processAddress("12higDjoCCNXSA95xZMWUdPvXNmkAduhWv");
//        addressProcessor.printStats();
//        addressProcessor.printMeta();

        ExportService exportService = new CSVExportService();
        exportService.export("out");
//        exportService.exportMeta("meta");
    }

/*
Number of addresses : 46791
Total               : 2325000.0
Total spent < 10%   : 2303761.41
Total unspent       : 2279081.75
 */

}
