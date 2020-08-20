package uk.ac.sanger.sprint.service.status;

import cab.*;
import uk.ac.sanger.sprint.model.Credentials;
import uk.ac.sanger.sprint.model.PrintStatus;
import uk.ac.sanger.sprint.model.Printer;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URL;

/**
 * An adapter for checking print status over SOAP (for CAB printers).
 */
public class SoapStatusProtocolAdapter implements StatusProtocolAdapter {
    private Printer printer;
    private SoapStatusAuthenticatorFactory soapStatusAuthenticatorFactory;

    public SoapStatusProtocolAdapter(Printer printer, SoapStatusAuthenticatorFactory soapStatusAuthenticatorFactory) {
        this.printer = printer;
        this.soapStatusAuthenticatorFactory = soapStatusAuthenticatorFactory;
    }

    @Override
    public PrintStatus getPrintStatus(String jobId) throws IOException {
        Credentials soapCredentials = printer.getPrinterType().getSoapCredentials();
        if (soapCredentials != null) {
            Authenticator authenticator = soapStatusAuthenticatorFactory.getAuthenticator(soapCredentials);
            Authenticator.setDefault(authenticator);
        }
        URL wsdlUrl = new URL(String.format("http://%s/cgi-bin/soap/services.wsdl",
                    printer.getAddress()));
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
