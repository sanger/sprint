package uk.ac.sanger.printy;

import cab.CabPrinterSOAP;
import cab.CabPrinterWebService;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.config.ConfigLoader;
import uk.ac.sanger.printy.model.*;
import uk.ac.sanger.printy.service.PrintService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
public class GraphQLDataFetchers {
    private final ObjectMapper objectMapper;
    private final PrintService printService;

    private final Map<String, Printer> printers;

    public GraphQLDataFetchers(ConfigLoader configLoader, ObjectMapper objectMapper, PrintService printService,
                               ApplicationArguments arguments) {
        requireNonNull(configLoader, "configLoader is null");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper is null");
        this.printService = requireNonNull(printService, "printService is null");
        requireNonNull(arguments, "applicationArguments is null");
        List<String> configArgs = arguments.getOptionValues("config");
        List<Path> configPaths;
        if (configArgs==null || configArgs.isEmpty()) {
            configPaths = Collections.singletonList(Paths.get("printers.xml"));
        } else {
            configPaths = configArgs.stream()
                    .map(Paths::get)
                    .collect(Collectors.toList());
        }
        this.printers = configLoader.getPrinters(configPaths);
    }

    public DataFetcher getPrinters() {
        return dataFetchingEnvironment -> {
            String labelTypeName = dataFetchingEnvironment.getArgument("labelType");
            if (labelTypeName==null) {
                return new ArrayList<>(printers.values());
            }
            return printers.values().stream()
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
        Printer printer = printers.get(hostname);
        if (printer==null) {
            throw new IllegalArgumentException("No such printer");
        }
        return printer;
    }

    public DataFetcher print() {
        return dataFetchingEnvironment -> {
            Map<String, String> map = dataFetchingEnvironment.getArgument("printRequest");
            PrintRequest req = objectMapper.convertValue(map, PrintRequest.class);
            String printerName = dataFetchingEnvironment.getArgument("printer");
            Printer printer = getPrinter(printerName);
            String jobId = printService.print(req, printer);

            return new PrintResult(jobId);
        };
    }

    public DataFetcher getPrintStatus() {
        return dataFetchingEnvironment -> {
            String joinedId = dataFetchingEnvironment.getArgument("jobId");
            int colon = joinedId.lastIndexOf(":");
            String printerName = joinedId.substring(0, colon);
            String jobId = joinedId.substring(colon+1);
            Printer printer = getPrinter(printerName);
            return printService.getPrintStatus(printer, jobId);
        };
    }
}