//package org.example.testkra.soap;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//
//@Configuration
//public class CountryConfiguration {
//
//  @Bean
//  public Jaxb2Marshaller marshaller() {
//    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//    // this package must match the package in the <generatePackage> specified in
//    // pom.xml
//    marshaller.setContextPath("generated");
//    return marshaller;
//  }
//
////  @Bean
////  public CountryClient countryClient(Jaxb2Marshaller marshaller) {
////    CountryClient client = new CountryClient();
////    client.setDefaultUri("https://196.61.52.30/");
////    client.setMarshaller(marshaller);
////    client.setUnmarshaller(marshaller);
////    return client;
////  }
//
//}