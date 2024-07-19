package org.example.testkra;

import org.apache.commons.codec.digest.DigestUtils;

public class TestPasswords {
    public static final String loginIdPP = "JUBILEEPINTEST";
    public static final String passwordPP = "Password@1";
    public static final String typeOfTaxpayerPP = "KE";
    public static final String taxpayerIDPP = "36670845";

//    loginId="JUBILEEPINTEST"
//    password="Password@1"
//    typeOfTaxpayer="KE"
//    taxpayerID="36670845"
//
//    curl -k -X POST "${uat_url}" \
//            -H "Content-Type: application/x-www-form-urlencoded" \
//            --data-urlencode "loginId=${loginId}" \
//            --data-urlencode "password=${encryptedPassword}" \
//            --data-urlencode "typeOfTaxpayer=${typeOfTaxpayer}" \
//            --data-urlencode "taxpayerID=${taxpayerID}"
//
//    soap_request=$(cat <<EOF
//            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:itax="http://itax.example.com/">
//            <soapenv:Header/>
//            <soapenv:Body>
//            <itax:validateTaxpayerID>
//            <itax:loginId>${loginId}</itax:loginId>
//         <itax:password>${encryptedPassword}</itax:password>
//         <itax:typeOfTaxpayer>${typeOfTaxpayer}</itax:typeOfTaxpayer>
//         <itax:taxpayerID>${taxpayerID}</itax:taxpayerID>
//      </itax:validateTaxpayerID>
//   </soapenv:Body>
//</soapenv:Envelope>
//    EOF
//)

    public static void main(String[] args) {

        String encryptedPassword = DigestUtils.sha256Hex(passwordPP + loginIdPP);
        String encryptedTypeOfTaxpayer = DigestUtils.sha256Hex(typeOfTaxpayerPP);

        System.out.println(encryptedPassword);

    }
}
