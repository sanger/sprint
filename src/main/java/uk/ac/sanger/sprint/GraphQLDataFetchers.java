package uk.ac.sanger.sprint;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.config.Config;
import uk.ac.sanger.sprint.config.ConfigLoader;
import uk.ac.sanger.sprint.model.*;
import uk.ac.sanger.sprint.service.PrintService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
public class GraphQLDataFetchers {
    private final ObjectMapper objectMapper;
    private final PrintService printService;

    private final Config config;

    public GraphQLDataFetchers(ConfigLoader configLoader, ObjectMapper objectMapper, PrintService printService,
                               ApplicationArguments arguments) {
        requireNonNull(configLoader, "configLoader is null");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper is null");
        this.printService = requireNonNull(printService, "printService is null");
        requireNonNull(arguments, "applicationArguments is null");
        List<String> configArgs = arguments.getOptionValues("config");
        List<Path> configPaths;
        if (configArgs==null || configArgs.isEmpty()) {
            configPaths = Collections.singletonList(Paths.get("printers"));
        } else {
            configPaths = configArgs.stream()
                    .map(Paths::get)
                    .collect(Collectors.toList());
        }
        this.config = configLoader.loadConfig(configPaths);
    }

    public DataFetcher getPrinters() {
        return dataFetchingEnvironment -> {
            String labelTypeName = dataFetchingEnvironment.getArgument("labelType");
            if (labelTypeName==null) {
                return new ArrayList<>(config.getPrinters().values());
            }
            return config.getPrinters().values().stream()
                    .filter(p -> p.getLabelType().getName().equals(labelTypeName))
                    .collect(Collectors.toList());
        };
    }

    private Printer getPrinter(String hostname) {
        Printer printer = config.getPrinters().get(hostname);
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
            String printerTypeName = dataFetchingEnvironment.getArgument("printerType");


            PrinterType printerType = null;
            if (printerTypeName!=null) {
                printerType = config.getPrinterTypes().get(printerTypeName);
                if (printerType==null) {
                    throw new IllegalArgumentException("Unknown printer type: "+printerTypeName);
                }
            }

            Printer printer = config.getPrinters().get(printerName);
            if (printer==null && printerType==null) {
                throw new IllegalArgumentException("Unknown printer without explicit printer type: "+printerName);
            }
            if (printer==null) {
                printer = new Printer(printerName, printerType, null);
            } else if (printerType!=null) {
                printer = new Printer(printer.getHostname(), printerType, printer.getLabelType());
            }

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

    public DataFetcher getLabelTypes() {
        return dataFetchingEnvironment -> config.getLabelTypes();
    }

    public DataFetcher getPrinterTypes() {
        return dataFetchingEnvironment -> config.getPrinterTypes().values();
    }
}