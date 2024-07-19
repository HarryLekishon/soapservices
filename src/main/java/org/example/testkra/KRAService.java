package org.example.testkra;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.example.testkra.xml.CustomKRAException;
import org.example.testkra.xml.Envelope;
import org.example.testkra.xml.InnerResponse;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Optional;

@Service
public class KRAService {
    private static final String UAT_URL = "https://196.61.52.30/USSD/UssdServiceFacadeBean?wsdl";

    private final String loginIdPP = "JUBILEEPINTEST";
    private final String passwordPP = "Password@1";
    private final String typeOfTaxpayerPP = "KE";
    private final String taxpayerIDPP = "36690393";

    public Response validateTaxpayerIDEncrpy(String loginId, String password, String typeOfTaxpayer, String taxpayerID) {
        String xmlResponse = "";
        try {
            // Encrypt the password and typeOfTaxpayer
            String encryptedPassword = DigestUtils.sha256Hex(passwordPP + loginIdPP);
            System.out.println(encryptedPassword);
            String encryptedTypeOfTaxpayer = DigestUtils.sha256Hex(typeOfTaxpayerPP);

            // Create the XML request body
            String xmlRequest = createXMLRequest(loginIdPP, passwordPP, typeOfTaxpayer, taxpayerIDPP);

            // Send the HTTP POST request
            xmlResponse = sendPostRequest(UAT_URL, xmlRequest);
            System.out.println(xmlResponse);

            // Parse the XML response


        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseXMLResponse(xmlResponse);
    }

    public InnerResponse validateTaxpayerIDNoEncrpt(String loginId, String password, String typeOfTaxpayer, String taxpayerID) throws CustomKRAException {
        String xmlResponse = "";
        try {
            // Encrypt the password and typeOfTaxpayer
            String encryptedPassword = DigestUtils.sha256Hex(passwordPP + loginIdPP);
            String encryptedTypeOfTaxpayer = DigestUtils.sha256Hex(typeOfTaxpayerPP);

            // Create the XML request body
            String xmlRequest = createXMLRequest(loginIdPP, passwordPP, typeOfTaxpayer, taxpayerID);
            System.out.println(xmlRequest);
            // Send the HTTP POST request
            xmlResponse = sendPostRequest(UAT_URL, xmlRequest);

//            xmlResponse = sendPostRequest(UAT_URL + "(" + loginIdPP + "," + encryptedPassword + "," + typeOfTaxpayerPP + "," + taxpayerIDPP + ")");
            System.out.println(xmlResponse);
            // Parse the XML response

        } catch (Exception e) {
            e.printStackTrace();
        }
        Optional<InnerResponse> innerResponse = unmarshalString(xmlResponse);
        if(innerResponse.isPresent()){
            return innerResponse.get();
        }else throw new CustomKRAException("Res is null or html, unmarshalling failed!");
    }


    private Optional<InnerResponse> unmarshalString(String xmlResponse){
        InnerResponse innerResponse = null;
        try {
        JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmlResponse);
        Envelope envelope = (Envelope) unmarshaller.unmarshal(reader);

        String returnValue = envelope.getBody().getValidateTaxpayerIDResponse().getReturnValue();
        System.out.println("Raw return value: " + returnValue);

        // Unmarshal the nested XML
        returnValue = returnValue.replace("&lt;", "<").replace("&gt;", ">");
        JAXBContext innerContext = JAXBContext.newInstance(InnerResponse.class);
        Unmarshaller innerUnmarshaller = innerContext.createUnmarshaller();
        StringReader innerReader = new StringReader(returnValue);
        innerResponse = (InnerResponse) innerUnmarshaller.unmarshal(innerReader);

        System.out.println("Response Code: " + innerResponse.getResponseCode());
        System.out.println("Message: " + innerResponse.getMessage());
        System.out.println("Status: " + innerResponse.getStatus());

        // Print optional fields if present
        if (innerResponse.getPin() != null) {
            System.out.println("PIN: " + innerResponse.getPin());
        }

        if (innerResponse.getTaxpayerName() != null) {
            System.out.println("Taxpayer Name: " + innerResponse.getTaxpayerName());
        }


    } catch(JAXBException e) {
        e.printStackTrace();
    }
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            StringReader reader = new StringReader(xmlResponse);
//            Envelope envelope = (Envelope) unmarshaller.unmarshal(reader);
//            String returnValue = envelope.getBody().getValidateTaxpayerIDResponse().getReturnValue();
//            System.out.println("Raw return value: " + returnValue);
//
//            // Unmarshal the nested XML
//            JAXBContext innerContext = JAXBContext.newInstance(InnerResponse.class);
//            Unmarshaller innerUnmarshaller = innerContext.createUnmarshaller();
//            StringReader innerReader = new StringReader(returnValue);
//            innerResponse = (InnerResponse) innerUnmarshaller.unmarshal(innerReader);
//
//            System.out.println("Response Code: " + innerResponse.getResponseCode());
//            System.out.println("Message: " + innerResponse.getMessage());
//            System.out.println("Status: " + innerResponse.getStatus());
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
        return Optional.ofNullable(innerResponse);
    }

    private String createXMLRequest(String loginId, String password, String typeOfTaxpayer, String taxpayerID) {
        return
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:impl=\"http://impl.facade.ussd.kra.tcs.com/\">\n" +
                        "     <soapenv:Header/>\n" +
                        "     <soapenv:Body>\n" +
                        "        <impl:validateTaxpayerID>\n" +
                        "           <!--Optional:-->\n" +
                        "           <loginId>" + loginId + "</loginId>\n" +
                        "           <!--Optional:-->\n" +
                        "           <password>" + password + "</password>\n" +
                        "           <!--Optional:-->\n" +
                        "           <typeOfTaxpayer>" + typeOfTaxpayer + "</typeOfTaxpayer>\n" +
                        "           <!--Optional:-->\n" +
                        "           <taxpayerID>" + taxpayerID + "</taxpayerID>\n" +
                        "        </impl:validateTaxpayerID>\n" +
                        "     </soapenv:Body>\n" +
                        "  </soapenv:Envelope>'";
    }

    protected void disableSSLCheck(){
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String sendPostRequest(String url, String xmlRequest) throws Exception {
//        private String sendPostRequest(String url) throws Exception {

        //        this.disableSSLCheck();
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

//        SSLContext sslContext = SSLContexts.custom()
//                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//                .build();

        // Create SSLConnectionSocketFactory with NoopHostnameVerifier to ignore hostname verification
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                sslContext,
//                NoopHostnameVerifier.INSTANCE);

        // Create HttpClient with custom SSLConnectionSocketFactory
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .build();
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(xmlRequest, StandardCharsets.UTF_8));
        httpPost.setHeader("Content-Type", "application/xml");
        httpPost.setHeader("Accept", "application/xml");

        CloseableHttpResponse response = httpclient.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        response.close();
        return responseString;
    }

    private Response parseXMLResponse(String xmlResponse) {
        Response response = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlResponse);
            response = (Response) unmarshaller.unmarshal(reader);

            // Output the response
            System.out.println("Response Code: " + response.getResult().getResponseCode());
            System.out.println("Message: " + response.getResult().getMessage());
            System.out.println("Status: " + response.getResult().getStatus());
            System.out.println("PIN: " + response.getResult().getPIN());
            System.out.println("Taxpayer Name: " + response.getResult().getTaxpayerName());


        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return response;
    }



}
