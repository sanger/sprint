
package cab;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for printFormat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="printFormat">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objects">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="fObject" type="{http://www.cab.de/WSSchema}objectTypePrint" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="jobID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numbers" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "printFormat", propOrder = {
    "objects",
    "jobID",
    "numbers"
})
public class PrintFormat {

    @XmlElement(required = true)
    protected PrintFormat.Objects objects;
    @XmlElement(required = true)
    protected String jobID;
    protected int numbers;

    /**
     * Gets the value of the objects property.
     * 
     * @return
     *     possible object is
     *     {@link PrintFormat.Objects }
     *     
     */
    public PrintFormat.Objects getObjects() {
        return objects;
    }

    /**
     * Sets the value of the objects property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintFormat.Objects }
     *     
     */
    public void setObjects(PrintFormat.Objects value) {
        this.objects = value;
    }

    /**
     * Gets the value of the jobID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobID() {
        return jobID;
    }

    /**
     * Sets the value of the jobID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobID(String value) {
        this.jobID = value;
    }

    /**
     * Gets the value of the numbers property.
     * 
     */
    public int getNumbers() {
        return numbers;
    }

    /**
     * Sets the value of the numbers property.
     * 
     */
    public void setNumbers(int value) {
        this.numbers = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="fObject" type="{http://www.cab.de/WSSchema}objectTypePrint" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fObject"
    })
    public static class Objects {

        protected List<ObjectTypePrint> fObject;

        /**
         * Gets the value of the fObject property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the fObject property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFObject().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ObjectTypePrint }
         * 
         * 
         */
        public List<ObjectTypePrint> getFObject() {
            if (fObject == null) {
                fObject = new ArrayList<ObjectTypePrint>();
            }
            return this.fObject;
        }

    }

}
