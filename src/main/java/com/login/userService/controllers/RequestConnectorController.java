//package com.login.userService.controllers;
//
//import com.login.userService.services.ProductServiceRestClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController  // by naveen
//public class RequestConnectorController {
//    private ProductServiceRestClient productServiceRestClient;
//
//    public RequestConnectorController(ProductServiceRestClient productServiceRestClient){
//        this.productServiceRestClient=productServiceRestClient;
//    }
//
//    @GetMapping("/allProduct")
//    public void getAllProductFromUser() {
//         productServiceRestClient.getProductDetails();
//    }
//
//}
