package org.example.testkra.rmi;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Service
public class Client {

    private final String loginIdPP = "JUBILEEPINTEST";
    private final String passwordPP = "Password@1";
    private final String typeOfTaxpayerPP = "KE";
    private final String taxpayerIDPP = "1234567890";

    String encryptedPassword = DigestUtils.sha256Hex(passwordPP + loginIdPP);
    String encryptedTypeOfTaxpayer = DigestUtils.sha256Hex(typeOfTaxpayerPP);

    public String callRemoteMethod(){
        String response = "";
        try {
            // Replace with the hostname and port of the RMI registry where the service is registered
            String host = "rmi://196.61.52.30";
            int port = 443;

            // Look up the RMI registry for the service
            Registry registry = LocateRegistry.getRegistry(host, port);

            // Look up the remote service by name
            TaxpayerValidationService service = (TaxpayerValidationService) registry.lookup("TaxpayerValidationService");

            // Invoke the remote method
            String loginId = loginIdPP;
            String password = encryptedPassword;
            String typeOfTaxpayer = typeOfTaxpayerPP;
            String taxpayerID = taxpayerIDPP;

            response = service.validateTaxpayerID(loginId, password, typeOfTaxpayer, taxpayerID);

            // Print the response
            System.out.println("Validation response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
