//package com.login.userService.services;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class ProductServiceRestClient {  //by naveen
//
//    private RestTemplate restTemplate;
//    private String productServiceUrl = "http://localhost:9090/products";
//
//    public ProductServiceRestClient(RestTemplate restTemplate ){
//        this.restTemplate=restTemplate;
//    }
//
//    public ResponseEntity<?> getProductDetails(){
//        System.out.println("Product Details : ------------ ");
//       String s = String.valueOf(restTemplate.getForEntity(productServiceUrl,String.class));
//        System.out.println(s);
//
//        return null;
//    }
//
//
//}
