package uk.ac.sanger.printy;

import cab.CabPrinterSOAP;
import cab.CabPrinterWebService;
import cab.FaultResponse;
import cab.PrintFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.*;
import uk.ac.sanger.printy.service.StatusProtocolAdapter;
import uk.ac.sanger.printy.service.StatusProtocolAdapterFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class GraphQLDataFetchers {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final List<Printer> PRINTERS = Arrays.asList(
            new Printer("cgaptestbc", PrinterLanguage.jscript, Protocol.ftp, StatusProtocol.soap),
            new Printer("d304bc", PrinterLanguage.tec, Protocol.lpd, null)
    );

    public DataFetcher getPrinters() {
        return dataFetchingEnvironment -> PRINTERS;
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

            CabPrinterSOAP printerWebServiceSOAP = new CabPrinterWebService().getPrinterWebServiceSOAP();

            final String uuid = UUID.randomUUID().toString();
            PrintFormat pf = new PrintFormat();
            pf.setJobID(uuid);
            pf.setNumbers(1);
            pf.setObjects(new PrintFormat.Objects());

            printerWebServiceSOAP.printFormat(pf);

            return new PrintResult(uuid);
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