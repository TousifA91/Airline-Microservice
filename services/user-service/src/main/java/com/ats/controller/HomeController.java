package com.ats.controller;

import com.ats.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        ApiResponse response = new ApiResponse("Welcome to User service home controller....");
        return response;
    }
}
