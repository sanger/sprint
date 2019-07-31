package uk.ac.sanger.printy;

import cab.CabPrinterSOAP;
import cab.CabPrinterWebService;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.config.PrinterConfig;
import uk.ac.sanger.printy.model.*;
import uk.ac.sanger.printy.service.*;

import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Map<String, Printer> PRINTERS = PrinterConfig.loadPrinters(Paths.get("."));

    public DataFetcher getPrinters() {
        return dataFetchingEnvironment -> {
            String labelTypeName = dataFetchingEnvironment.getArgument("labelType");
            if (labelTypeName==null) {
                return PRINTERS;
            }
            return PRINTERS.values().stream()
                    .filter(p -> p.getLabelType().getName().equals(labelTypeName))
                    .collect(Collectors.toList());
        };
    }

    public DataFetcher getPrinterStatus() {
        return dataFetchingEnvironment -> {
            Printer printer = dataFetchingEnvironment.getSource();
            if (printer.getPrinterType().getStatusProtocol()==null) {
                return null;
            }
            CabPrinterSOAP printerWebServiceSOAP = new CabPrinterWebService().getPrinterWebServiceSOAP();
            return printerWebServiceSOAP.getPrinterStatus("");
        };
    }

    private Printer getPrinter(String hostname) {
        Printer printer = PRINTERS.get(hostname);
        if (printer==null) {
            throw new IllegalArgumentException("No such printer");
        }
        return printer;
    }

    public DataFetcher print() {
        return dataFetchingEnvironment -> {
            Map<String, String> map = dataFetchingEnvironment.getArgument("printRequest");
            PrintRequest req = OBJECT_MAPPER.convertValue(map, PrintRequest.class);
            String printerName = dataFetchingEnvironment.getArgument("printer");
            Printer printer = getPrinter(printerName);
            PrintService printService = new PrintService();
            String jobId = printService.print(req, printer);

            return new PrintResult(jobId);
        };
    }

    public DataFetcher isJobComplete() {
        return dataFetchingEnvironment -> {
            String joinedId = dataFetchingEnvironment.getArgument("id");
            int colon = joinedId.lastIndexOf(":");
            String printerName = joinedId.substring(0, colon);
            String jobId = joinedId.substring(colon+1);

            Printer printer = getPrinter(printerName);

            PrintService printService = new PrintService();
            return printService.isJobComplete(printer, jobId);
        };
    }
}