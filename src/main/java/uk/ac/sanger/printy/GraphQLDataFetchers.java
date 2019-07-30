package uk.ac.sanger.printy;

import cab.CabPrinterSOAP;
import cab.CabPrinterWebService;
import cab.PrintFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.*;
import uk.ac.sanger.printy.service.PrintService;
import uk.ac.sanger.printy.service.StatusProtocolAdapter;
import uk.ac.sanger.printy.service.StatusProtocolAdapterFactory;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Map<String, LabelType> LABEL_TYPES = Collections.singletonMap("tiny", new LabelType(30, 12, 15, "tiny"));

    private static final List<Printer> PRINTERS = Arrays.asList(
            new Printer("cgaptestbc", PrinterLanguage.jscript, Protocol.ftp, StatusProtocol.soap, LABEL_TYPES.get("tiny")),
            new Printer("d304bc", PrinterLanguage.tec, Protocol.lpd, null, LABEL_TYPES.get("tiny"))
    );

    public DataFetcher getPrinters() {
        return dataFetchingEnvironment -> {
            String labelTypeName = dataFetchingEnvironment.getArgument("labelType");
            if (labelTypeName==null) {
                return PRINTERS;
            }
            return PRINTERS.stream()
                    .filter(p -> p.getLabelType().getName().equals(labelTypeName))
                    .collect(Collectors.toList());
        };
    }

    public DataFetcher getPrinterStatus() {
        return dataFetchingEnvironment -> {
            Printer printer = dataFetchingEnvironment.getSource();
            if (printer.getStatusProtocol()==null) {
                return null;
            }
            CabPrinterSOAP printerWebServiceSOAP = new CabPrinterWebService().getPrinterWebServiceSOAP();
            return printerWebServiceSOAP.getPrinterStatus("");
        };
    }

    public DataFetcher print() {
        return dataFetchingEnvironment -> {
            Map<String, String> map = dataFetchingEnvironment.getArgument("printRequest");
            PrintRequest req = OBJECT_MAPPER.convertValue(map, PrintRequest.class);
            String printerName = dataFetchingEnvironment.getArgument("printer");
            Printer printer = PRINTERS.stream()
                    .filter(p -> p.getName().equals(printerName))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No such printer"));
            PrintService printService = new PrintService();
            String jobId = printService.print(req, printer);

            return new PrintResult(jobId);
        };
    }

    public DataFetcher getPrintStatus() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            String printerArg = dataFetchingEnvironment.getArgument("printer");

            Printer printer = PRINTERS.stream().filter(p -> p.getName().equals(printerArg))
                    .findAny().orElseGet(null);

            StatusProtocolAdapter statusProtocolAdapter = StatusProtocolAdapterFactory.getStatusProtocolAdapter(printer);
            return statusProtocolAdapter.getStatus(id);
        };
    }
}