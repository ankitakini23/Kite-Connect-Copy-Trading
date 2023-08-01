package com.example.copytrade.controllers;

import com.example.copytrade.services.CopyTradingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    CopyTradingService copyTradingService;
    @GetMapping(value = "/testKite")
    public void testKite(){
        copyTradingService.testKite();
    }
}
