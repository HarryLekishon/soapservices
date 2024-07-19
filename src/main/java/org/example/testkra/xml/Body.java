package org.example.testkra.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class Body {
    private ValidateTaxpayerIDResponse validateTaxpayerIDResponse;

    @XmlElement(name = "validateTaxpayerIDResponse", namespace = "http://impl.facade.ussd.kra.tcs.com/")
    public ValidateTaxpayerIDResponse getValidateTaxpayerIDResponse() {
        return validateTaxpayerIDResponse;
    }

    public void setValidateTaxpayerIDResponse(ValidateTaxpayerIDResponse validateTaxpayerIDResponse) {
        this.validateTaxpayerIDResponse = validateTaxpayerIDResponse;
    }
}
