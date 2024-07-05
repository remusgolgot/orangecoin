package main;

import services.CSVExportService;
import services.ExportService;

public class MainExport {

    public static void main(String[] args) {
        ExportService exportService = new CSVExportService();
        exportService.export("out");
    }
}
