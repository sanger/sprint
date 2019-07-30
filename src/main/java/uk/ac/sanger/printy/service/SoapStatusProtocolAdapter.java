package uk.ac.sanger.printy.service;

import cab.*;
import uk.ac.sanger.printy.model.Printer;

public class SoapStatusProtocolAdapter implements StatusProtocolAdapter {
    private Printer printer;

    public SoapStatusProtocolAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public boolean isJobComplete(String jobId) {
        CabPrinterWebService service =  new CabPrinterWebService();
        CabPrinterSOAP printerWebServiceSOAP = service.getPrinterWebServiceSOAP();
        ListOfJobsResponse response;
        try {
            response = printerWebServiceSOAP.getlistOfJobs(new ListOfJobsRequest());
        } catch (FaultResponse faultResponse) {
            faultResponse.printStackTrace();
            return false;
        }

        return jobFinished(response, jobId);
    }

    private boolean jobFinished(ListOfJobsResponse response, String jobId) {
        boolean anyFound = false;
        for (Job job : response.getJob()) {
            if (job.getId().equals(jobId)) {
                anyFound = true;
                if (!job.getStatus().equalsIgnoreCase("finished")) {
                    return false;
                }
            }
        }
        return anyFound;
    }
}
