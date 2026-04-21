package com.ats.controller;

import com.ats.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public ApiResponse homeController(){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello! welcome to airline home controller .......");
        //return "Hello! welcome to airline home controller .......  ";
        return apiResponse;
    }
}
