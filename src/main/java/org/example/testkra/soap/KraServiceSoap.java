//package org.example.testkra.soap;
//
//import generated.RESPONSE;
//import generated.ValidateTaxpayerIDRequest;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//import org.springframework.stereotype.Service;
//import org.springframework.ws.client.core.WebServiceTemplate;
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//
//@Service
//
//public class KraServiceSoap extends WebServiceGatewaySupport{
//
//    private final TrustAllCertificatesManager trustAllCertificatesManager;
//
//    private WebServiceTemplate template;
//    private final Jaxb2Marshaller jaxb2Marshaller;
//
//    private final String loginIdPP = "JUBILEEPINTEST";
//    private final String passwordPP = "Password@1";
//    private final String typeOfTaxpayerPP = "KE";
//    private final String taxpayerIDPP = "1234567890";
//
//    String encryptedPassword = DigestUtils.sha256Hex(passwordPP + loginIdPP);
//    String encryptedTypeOfTaxpayer = DigestUtils.sha256Hex(typeOfTaxpayerPP);
//
//    public KraServiceSoap(TrustAllCertificatesManager trustAllCertificatesManager, Jaxb2Marshaller jaxb2Marshaller) {
//        this.trustAllCertificatesManager = trustAllCertificatesManager;
//        this.jaxb2Marshaller = jaxb2Marshaller;
//    }
//
//    public RESPONSE postToKraTaxValidationService(String typeOfTaxpayer, String taxpayerID) {
// trustAllCertificatesManager.disableSSLCertificateChecking();
//        ValidateTaxpayerIDRequest validateTaxpayerIDRequest = new ValidateTaxpayerIDRequest();
//        validateTaxpayerIDRequest.setTaxpayerID(taxpayerIDPP);
//        validateTaxpayerIDRequest.setTypeOfTaxpayer(typeOfTaxpayerPP);
//        validateTaxpayerIDRequest.setLoginId(loginIdPP);
//        validateTaxpayerIDRequest.setPassword(encryptedPassword);
//        template = new WebServiceTemplate(jaxb2Marshaller);
//        return (RESPONSE) template.marshalSendAndReceive("https://196.61.52.30/",
//                validateTaxpayerIDRequest);
//    }
//
//}
