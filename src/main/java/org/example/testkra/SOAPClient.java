//package org.example.testkra;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.cxf.configuration.jsse.TLSClientParameters;
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//import org.apache.cxf.transport.http.HTTPConduit;
//import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
//import org.springframework.stereotype.Service;
//
//import javax.net.ssl.*;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//
//@Service
//public class SOAPClient {
//
//    private final String loginIdPP = "JUBILEEPINTEST";
//    private final String passwordPP = "Password@1";
//    private final String typeOfTaxpayerPP = "KE";
//    private final String taxpayerIDPP = "1234567890";
//
//    String encryptedPassword = DigestUtils.sha256Hex(passwordPP + loginIdPP);
//    String encryptedTypeOfTaxpayer = DigestUtils.sha256Hex(typeOfTaxpayerPP);
//
//    public Object getApacheClient() throws Exception {
//        disableSslVerification();
//        System.setProperty("com.sun.net.ssl.checkRevocation", "false");
//        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
//        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification", "true");
//        // Create a JAX-WS dynamic client factory
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//
//        // Create a client from the WSDL URL
////        Client client = dcf.createClient("https://196.61.52.30/service?wsdl");
//        Client client = dcf.createClient("https://196.61.52.30/");
//
//
//        // Get the HTTP conduit from the client
//        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
//
//        // Set up the HTTP client policy
//        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//        httpClientPolicy.setConnectionTimeout(36000);
//        httpClientPolicy.setReceiveTimeout(32000);
//        httpConduit.setClient(httpClientPolicy);
//
//        // Set up TLS client parameters
//        TLSClientParameters tlsParams = new TLSClientParameters();
//        tlsParams.setDisableCNCheck(true); // Disable Common Name check
//
//        // Trust all certificates
//        TrustManager[] trustAllCerts = new TrustManager[] {
//                new X509TrustManager() {
//                    public X509Certificate[] getAcceptedIssuers() { return null; }
//                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
//                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
//                }
//        };
//        SSLContext sc = SSLContext.getInstance("TLS");
//        sc.init(null, trustAllCerts, new java.security.SecureRandom());
//        tlsParams.setSSLSocketFactory(sc.getSocketFactory());
//
//        // Set the custom hostname verifier
//        tlsParams.setHostnameVerifier(new HostnameVerifier() {
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
//
//        httpConduit.setTlsClientParameters(tlsParams);
//
//        // Make the SOAP request
//        Object[] response = client.invoke("validateTaxpayerID", "JUBILEEPINTEST", encryptedPassword,
//                typeOfTaxpayerPP, taxpayerIDPP);
//        System.out.println(response[0].toString());
//        return response[0];
//    }
//
//    private static void disableSslVerification() throws NoSuchAlgorithmException, KeyManagementException {
//        try {
//            // Create a trust manager that does not validate certificate chains
//            TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return null;
//                        }
//
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//            // Install the all-trusting host name verifier
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            };
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//        } catch (NoSuchAlgorithmException | KeyManagementException e) {
//            e.printStackTrace();
//        }
//    }
//}
package org.example.testkra;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
public class SOAPClient {

    private final String loginId = "JUBILEEPINTEST";
    private final String password = "Password@1";
    private final String typeOfTaxpayer = "KE";
    private final String taxpayerID = "36690393";

    public Object getApacheClient() throws Exception {
        disableSslVerification();
        System.out.println("pass ");


        // Create a JAX-WS dynamic client factory
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

        // Create a client from the WSDL URL
        Client client = dcf.createClient("https://196.61.52.30/USSD/UssdServiceFacadeBean?wsdl");

        // Check if WSDL URL is accessible
        String wsdlUrl = "https://196.61.52.30/USSD/UssdServiceFacadeBean?wsdl";
        if (!isUrlAccessible(wsdlUrl)) {
            throw new FileNotFoundException("WSDL URL not accessible: " + wsdlUrl);
        }

        // Get the HTTP conduit from the client
        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();

        // Set up the HTTP client policy
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(36000);
        httpClientPolicy.setReceiveTimeout(32000);
        httpConduit.setClient(httpClientPolicy);

        // Set up TLS client parameters
        TLSClientParameters tlsParams = new TLSClientParameters();
        tlsParams.setDisableCNCheck(true); // Disable Common Name check

        // Trust all certificates
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
        };
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        tlsParams.setSSLSocketFactory(sc.getSocketFactory());

        // Set the custom hostname verifier
        tlsParams.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        httpConduit.setTlsClientParameters(tlsParams);

        // Make the SOAP request and print raw XML response for debugging
        String soapAction = "validateTaxpayerID";
        String endpoint = "https://196.61.52.30/USSD/UssdServiceFacadeBean?wsdl";
        String soapRequest = getSoapRequest("JUBILEEPINTEST", password , typeOfTaxpayer, taxpayerID);

        System.out.println("SOAP Request: \n" + soapRequest);

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        connection.setRequestProperty("SOAPAction", soapAction);
        connection.setDoOutput(true);
        connection.getOutputStream().write(soapRequest.getBytes());

        InputStream responseStream = connection.getInputStream();
        byte[] responseBytes = responseStream.readAllBytes();
        String rawResponse = new String(responseBytes);

        System.out.println("Raw SOAP Response: \n" + rawResponse);

        // Parse and return the response
        Object[] response = client.invoke(soapAction, "JUBILEEPINTEST", password,
                typeOfTaxpayer, taxpayerID);
        System.out.println(response[0]);
        return response[0];
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Install the all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private String getSoapRequest(String loginId, String password, String typeOfTaxpayer, String taxpayerId) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://service.example.org/\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + "<ns:validateTaxpayerID>"
                + "<loginId>" + loginId + "</loginId>"
                + "<password>" + password + "</password>"
                + "<typeOfTaxpayer>" + typeOfTaxpayer + "</typeOfTaxpayer>"
                + "<taxpayerID>" + taxpayerId + "</taxpayerID>"
                + "</ns:validateTaxpayerID>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";
    }

    private boolean isUrlAccessible(String urlString) {
        try {
            System.out.println("checking accessibility");
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();

            int responseCode = connection.getResponseCode();
            System.out.println("not accessible");
            return responseCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
