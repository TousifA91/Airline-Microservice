package com.ats.controller;

import com.ats.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        ApiResponse response = new ApiResponse("Hello everyone! welcome to airline operations service....");
        return response;
    }
}
