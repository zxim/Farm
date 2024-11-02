package com.Hanium.Farm.Farm.Controller;

import com.Hanium.Farm.Farm.Domain.Fruit;
import com.Hanium.Farm.Farm.Domain.PeriodFruit;
import com.Hanium.Farm.Farm.Domain.RecommendFruit;
import com.Hanium.Farm.Farm.Service.FruitService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class FruitController {

    FruitService fruitService;

    @Autowired
    public FruitController(FruitService fruitService){
        this.fruitService = fruitService;
    }

    @GetMapping("/search")
    @ResponseBody
    public String getFruitInfo(@RequestParam String fruit){ // 과일의 정보를 받아 client로 보내준다.
        Fruit info = fruitService.getFruitInfo(fruit);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String temp = gson.toJson(info);
        return temp;
    }

    @GetMapping("/period")
    @ResponseBody
    public ArrayList<PeriodFruit> getPeriodFruits(@RequestParam int month){
        ArrayList<PeriodFruit> fruits =  fruitService.getPeriodFruits(month);
        return fruits;
    }

    @GetMapping("/recommend")
    @ResponseBody
    public ArrayList<RecommendFruit> recommendFruit(@RequestParam String[] nutrition){
        ArrayList<RecommendFruit> fruits = fruitService.getRecommendFruits(nutrition);
        return fruits;
    }

    @GetMapping("/fruitnames")
    @ResponseBody
    public ArrayList<String> getFruitNames(){
        ArrayList<String> names = fruitService.getFruitNames();
        return names;
    }

    // 과일의 이름을 띄어쓰기를 구분자로 해서 받는다.
    @GetMapping("hotFruits")
    public String getHotFruits(){
        return fruitService.getHotFruits();
    }
}
