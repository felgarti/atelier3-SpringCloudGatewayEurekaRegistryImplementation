package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;
@RestController
public class billController {

//
//            @Autowired
//        private StreamBridge streamBridge ;
//        @GetMapping("/publish")
//        public Bill publish(){
//
//            Bill bill= new Bill(null , new Date() ,null , new Random().nextLong(4L),null) ; streamBridge.send("R1"  , bill) ;
//            return bill;
//        }

}
