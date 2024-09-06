package com.btc;

import com.btc.services.CSVExportService;
import com.btc.services.ExportService;

public class MainExport {

    public static void main(String[] args) {
        ExportService exportService = new CSVExportService();
        exportService.export("out");
    }
}
