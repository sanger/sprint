
package cab;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cab package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PrintFormatRequest_QNAME = new QName("http://www.cab.de/WSSchema", "printFormatRequest");
    private final static QName _PrintFormatResponse_QNAME = new QName("http://www.cab.de/WSSchema", "printFormatResponse");
    private final static QName _FaultResponse_QNAME = new QName("http://www.cab.de/WSSchema", "faultResponse");
    private final static QName _LoadFormatResponse_QNAME = new QName("http://www.cab.de/WSSchema", "loadFormatResponse");
    private final static QName _LoadFormatRequest_QNAME = new QName("http://www.cab.de/WSSchema", "loadFormatRequest");
    private final static QName _StatusRequest_QNAME = new QName("http://www.cab.de/WSSchema", "statusRequest");
    private final static QName _StatusResponse_QNAME = new QName("http://www.cab.de/WSSchema", "statusResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cab
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrintFormat }
     * 
     */
    public PrintFormat createPrintFormat() {
        return new PrintFormat();
    }

    /**
     * Create an instance of {@link ListOfObjectsRequest }
     * 
     */
    public ListOfObjectsRequest createListOfObjectsRequest() {
        return new ListOfObjectsRequest();
    }

    /**
     * Create an instance of {@link AbortAllJobsRequest }
     * 
     */
    public AbortAllJobsRequest createAbortAllJobsRequest() {
        return new AbortAllJobsRequest();
    }

    /**
     * Create an instance of {@link ListOfFormatsRequest }
     * 
     */
    public ListOfFormatsRequest createListOfFormatsRequest() {
        return new ListOfFormatsRequest();
    }

    /**
     * Create an instance of {@link ListOfJobsResponse }
     * 
     */
    public ListOfJobsResponse createListOfJobsResponse() {
        return new ListOfJobsResponse();
    }

    /**
     * Create an instance of {@link Job }
     * 
     */
    public Job createJob() {
        return new Job();
    }

    /**
     * Create an instance of {@link AbortAllJobsResponse }
     * 
     */
    public AbortAllJobsResponse createAbortAllJobsResponse() {
        return new AbortAllJobsResponse();
    }

    /**
     * Create an instance of {@link ListOfFormatsResponse }
     * 
     */
    public ListOfFormatsResponse createListOfFormatsResponse() {
        return new ListOfFormatsResponse();
    }

    /**
     * Create an instance of {@link Format }
     * 
     */
    public Format createFormat() {
        return new Format();
    }

    /**
     * Create an instance of {@link ListOfObjectsResponse }
     * 
     */
    public ListOfObjectsResponse createListOfObjectsResponse() {
        return new ListOfObjectsResponse();
    }

    /**
     * Create an instance of {@link ObjectType }
     * 
     */
    public ObjectType createObjectType() {
        return new ObjectType();
    }

    /**
     * Create an instance of {@link LoadFormat }
     * 
     */
    public LoadFormat createLoadFormat() {
        return new LoadFormat();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link ListOfJobsRequest }
     * 
     */
    public ListOfJobsRequest createListOfJobsRequest() {
        return new ListOfJobsRequest();
    }

    /**
     * Create an instance of {@link ObjectTypePrint }
     * 
     */
    public ObjectTypePrint createObjectTypePrint() {
        return new ObjectTypePrint();
    }

    /**
     * Create an instance of {@link AbortAllJobs }
     * 
     */
    public AbortAllJobs createAbortAllJobs() {
        return new AbortAllJobs();
    }

    /**
     * Create an instance of {@link PrintFormat.Objects }
     * 
     */
    public PrintFormat.Objects createPrintFormatObjects() {
        return new PrintFormat.Objects();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintFormat }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cab.de/WSSchema", name = "printFormatRequest")
    public JAXBElement<PrintFormat> createPrintFormatRequest(PrintFormat value) {
        return new JAXBElement<PrintFormat>(_PrintFormatRequest_QNAME, PrintFormat.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadFormat }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cab.de/WSSchema", name = "printFormatResponse")
    public JAXBElement<LoadFormat> createPrintFormatResponse(LoadFormat value) {
        return new JAXBElement<LoadFormat>(_PrintFormatResponse_QNAME, LoadFormat.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cab.de/WSSchema", name = "faultResponse")
    public JAXBElement<FaultType> createFaultResponse(FaultType value) {
        return new JAXBElement<FaultType>(_FaultResponse_QNAME, FaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadFormat }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cab.de/WSSchema", name = "loadFormatResponse")
    public JAXBElement<LoadFormat> createLoadFormatResponse(LoadFormat value) {
        return new JAXBElement<LoadFormat>(_LoadFormatResponse_QNAME, LoadFormat.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cab.de/WSSchema", name = "loadFormatRequest")
    public JAXBElement<String> createLoadFormatRequest(String value) {
        return new JAXBElement<String>(_LoadFormatRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cab.de/WSSchema", name = "statusRequest")
    public JAXBElement<String> createStatusRequest(String value) {
        return new JAXBElement<String>(_StatusRequest_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cab.de/WSSchema", name = "statusResponse")
    public JAXBElement<String> createStatusResponse(String value) {
        return new JAXBElement<String>(_StatusResponse_QNAME, String.class, null, value);
    }

}
