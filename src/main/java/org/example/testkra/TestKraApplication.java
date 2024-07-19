package org.example.testkra;

import lombok.AllArgsConstructor;
import org.example.testkra.rmi.Client;
//import org.example.testkra.soap.KraServiceSoap;
import org.example.testkra.xml.ApiResponse;
import org.example.testkra.xml.CustomKRAException;
import org.example.testkra.xml.InnerResponse;
import org.example.testkra.xml.TypeOfTaxpayer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SpringBootApplication
@RestController("/api/v1")
@AllArgsConstructor
public class TestKraApplication {

    private final SOAPClient soapClient;
//    private final KraServiceSoap kraServiceSoap;

    private final Client client;

    private final KRAService kraService;

    public static void main(String[] args) {
        SpringApplication.run(TestKraApplication.class, args);
    }


//    validateTaxpayerID(loginId, password, typeOfTaxpayer, taxpayerID)
//
//    @RequestMapping(method = RequestMethod.GET, value = "/validate",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getObject(){
//        Response response = kraService.validateTaxpayerIDEncrpy("a", "b", "c", "d");
//        return ResponseEntity.ok(response.getResult());
//    }


    @RequestMapping(method = RequestMethod.GET, value = "/kra_validate-id",
            params = {"taxpayerID", "typeOfTaxpayer"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<InnerResponse>> validateKraNic(
            @RequestParam(required = true, value = "taxpayerID") String taxpayerID,
            @RequestParam(required = true, value = "typeOfTaxpayer") TypeOfTaxpayer typeOfTaxpayer
    ){
        InnerResponse innerResponse = new InnerResponse();
        try {
            innerResponse = kraService.validateTaxpayerIDNoEncrpt("a", "b",
                    typeOfTaxpayer.getName(), taxpayerID);
        }catch (Exception ex){
            if(ex instanceof CustomKRAException){
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ApiResponse.errorPassStatus(
                        "FAILED to unmarshall " + ex.getMessage(), HttpStatus.EXPECTATION_FAILED
                ));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.errorPassStatus("500 Failed" +
                    ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return handleResponse(innerResponse);
//        Map<String, Function<InnerResponse, ResponseEntity<ApiResponse<InnerResponse>>>> responseMap = Map.of(
//                "30000", response -> ResponseEntity.ok(ApiResponse.success(response)),
//                "30001", response -> ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
//                        .body(ApiResponse.errorPassStatus("Contact Admin, Check Passwords", HttpStatus.PRECONDITION_FAILED)),
//                "30002", response -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(ApiResponse.errorPassStatus("ID entered is invalid", HttpStatus.BAD_REQUEST)),
//                "30003", response -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(ApiResponse.errorPassStatus("ITax is still processing, try again later", HttpStatus.INTERNAL_SERVER_ERROR))
//        );
//
//        return responseMap.getOrDefault(innerResponse.getResponseCode(),
//                response -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(ApiResponse.errorPassStatus("unknown!", HttpStatus.INTERNAL_SERVER_ERROR))
//        ).apply(innerResponse);
//
//        return switch (innerResponse.getResponseCode()) {
//            case "30000" -> ResponseEntity.ok(ApiResponse.success(innerResponse));
//            case "30001" -> ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ApiResponse.errorPassStatus(
//                    "Contact Admin, Check Passwords", HttpStatus.PRECONDITION_FAILED
//            ));
//            case "30002" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.errorPassStatus(
//                    "ID entered is invalid", HttpStatus.BAD_REQUEST
//            ));
//            case "30003" -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.errorPassStatus(
//                    "ITax is still processing, try again later", HttpStatus.INTERNAL_SERVER_ERROR
//            ));
//            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.errorPassStatus(
//                    "unknown!", HttpStatus.INTERNAL_SERVER_ERROR
//            ));
//        };
    }

    private ResponseEntity<ApiResponse<InnerResponse>> handleResponse(InnerResponse innerResponse) {
        // Initialize the map with response codes and their corresponding functions
        Map<String, Function<InnerResponse, ResponseEntity<ApiResponse<InnerResponse>>>> responseMap = new HashMap<>();
        responseMap.put("30000", response -> ResponseEntity.ok(ApiResponse.success(response)));
        responseMap.put("30001", response -> ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(ApiResponse.errorPassStatus("Contact Admin, Check Passwords", HttpStatus.PRECONDITION_FAILED)));
        responseMap.put("30002", response -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.errorPassStatus("ID entered is invalid", HttpStatus.BAD_REQUEST)));
        responseMap.put("30003", response -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.errorPassStatus("ITax is still processing, try again later", HttpStatus.INTERNAL_SERVER_ERROR)));

        // Default function for unknown response codes
        Function<InnerResponse, ResponseEntity<ApiResponse<InnerResponse>>> defaultFunction = response -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.errorPassStatus("unknown!", HttpStatus.INTERNAL_SERVER_ERROR));

        // Retrieve the function based on the response code, or use the default function if the code is not found
        return responseMap.getOrDefault(innerResponse.getResponseCode(), defaultFunction).apply(innerResponse);
    }




//    @RequestMapping(method = RequestMethod.GET, value = "/validate/rmi",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getResponseRMI(){
//        String s = client.callRemoteMethod();
//        return ResponseEntity.ok(s);
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/validate/soap",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getResponseSoap(){
//        RESPONSE response = kraServiceSoap.postToKraTaxValidationService("xx", "yy");
//        return ResponseEntity.ok(response.getRESULT());
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/validate/apacheClient",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getResponseApache() throws Exception {
//        Object apacheClient = soapClient.getApacheClient();
//        return ResponseEntity.ok(apacheClient);
//    }
}
