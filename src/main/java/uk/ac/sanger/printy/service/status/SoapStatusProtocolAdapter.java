package uk.ac.sanger.printy.service.status;

import cab.*;
import uk.ac.sanger.printy.model.PrintStatus;
import uk.ac.sanger.printy.model.Printer;

import java.io.IOException;
import java.net.URL;

/**
 * An adapter for checking print status over SOAP (for CAB printers).
 */
public class SoapStatusProtocolAdapter implements StatusProtocolAdapter {
    private Printer printer;

    public SoapStatusProtocolAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public PrintStatus getPrintStatus(String jobId) throws IOException {
        URL wsdlUrl = new URL(String.format("http://%s.internal.sanger.ac.uk/cgi-bin/soap/services.wsdl",
                    printer.getHostname()));
        CabPrinterWebService service = new CabPrinterWebService(wsdlUrl);
        CabPrinterSOAP printerWebServiceSOAP = service.getPrinterWebServiceSOAP();
        ListOfJobsResponse response;
        try {
            response = printerWebServiceSOAP.getlistOfJobs(new ListOfJobsRequest());
        } catch (FaultResponse e) {
            throw new IOException("Fault getting jobs from SOAP", e);
        }
        return printStatus(response, jobId);
    }

    private PrintStatus printStatus(ListOfJobsResponse response, String jobId) {
        int numFinished = 0;
        int numAborted = 0;
        for (Job job : response.getJob()) {
            if (job.getId().equals(jobId)) {
                switch (job.getStatus().toLowerCase()) {
                    case "finished":
                        ++numFinished;
                        break;
                    case "aborted":
                        ++numAborted;
                        break;
                }
            }
        }
        return new PrintStatus(numFinished, numAborted);
    }
}
