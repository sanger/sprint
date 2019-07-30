package uk.ac.sanger.printy;

import cab.CabPrinterSOAP;
import cab.CabPrinterWebService;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.*;
import uk.ac.sanger.printy.service.Credentials;
import uk.ac.sanger.printy.service.PrintService;
import uk.ac.sanger.printy.service.StatusProtocolAdapter;
import uk.ac.sanger.printy.service.StatusProtocolAdapterFactory;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Map<String, LabelType> LABEL_TYPES = Collections.singletonMap("tiny", new LabelType(30, 12, 15, "tiny"));
    private static final Map<String, PrinterType> PRINTER_TYPES = Collections.singletonMap("squix",
            new PrinterType("squix", PrinterLanguage.JSCRIPT, Protocol.FTP, StatusProtocol.SOAP,
            new Credentials("ftpprint", "print")));

    private static final List<Printer> PRINTERS = Collections.singletonList(
            new Printer("cgaptestbc", PRINTER_TYPES.get("squix"), LABEL_TYPES.get("tiny"))
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
            if (printer.getPrinterType().getStatusProtocol()==null) {
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
                    .filter(p -> p.getHostname().equals(printerName))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No such printer"));
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

            Printer printer = PRINTERS.stream().filter(p -> p.getHostname().equals(printerName))
                    .findAny().orElseThrow(() -> new IllegalArgumentException("No such printer"));

            StatusProtocolAdapter statusProtocolAdapter = StatusProtocolAdapterFactory.getStatusProtocolAdapter(printer);
            return statusProtocolAdapter.isJobComplete(jobId);
        };
    }
}