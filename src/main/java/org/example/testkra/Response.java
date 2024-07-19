package org.example.testkra;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RESPONSE")
public class Response {

    private Result result;

    @XmlElement(name = "RESULT")
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {

        private String responseCode;
        private String message;
        private String status;
        private String pin;
        private String taxpayerName;

        @XmlElement(name = "ResponseCode")
        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        @XmlElement(name = "Message")
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @XmlElement(name = "Status")
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @XmlElement(name = "PIN")
        public String getPIN() {
            return pin;
        }

        public void setPIN(String pin) {
            this.pin = pin;
        }

        @XmlElement(name = "TaxpayerName")
        public String getTaxpayerName() {
            return taxpayerName;
        }

        public void setTaxpayerName(String taxpayerName) {
            this.taxpayerName = taxpayerName;
        }
    }
}