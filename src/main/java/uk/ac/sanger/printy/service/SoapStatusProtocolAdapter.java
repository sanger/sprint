package uk.ac.sanger.printy.service;

import cab.*;
import uk.ac.sanger.printy.model.PrintStatus;
import uk.ac.sanger.printy.model.Printer;

public class SoapStatusProtocolAdapter implements StatusProtocolAdapter {
    private Printer printer;

    public SoapStatusProtocolAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public PrintStatus getStatus(String jobId) {
        CabPrinterWebService service =  new CabPrinterWebService();
        CabPrinterSOAP printerWebServiceSOAP = service.getPrinterWebServiceSOAP();
        ListOfJobsResponse response;
        try {
            response = printerWebServiceSOAP.getlistOfJobs(new ListOfJobsRequest());
        } catch (FaultResponse faultResponse) {
            faultResponse.printStackTrace();
            return null; // TODO
        }
        Job job = response.getJob().stream().filter(j -> jobId.equals(j.getId()))
                .findAny().orElse(null);
        if (job==null) {
            return PrintStatus.notFound;
        }
        switch (job.getStatus().toLowerCase()) {
            case "finished": return PrintStatus.succeeded;
            // TODO
        }
        return PrintStatus.notFound; // TODO
    }
}
