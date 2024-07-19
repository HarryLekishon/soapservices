package org.example.testkra.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "validateTaxpayerIDResponse", namespace = "http://impl.facade.ussd.kra.tcs.com/")
public class ValidateTaxpayerIDResponse {
    private String returnValue;

    @XmlElement(name = "return")
    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}
