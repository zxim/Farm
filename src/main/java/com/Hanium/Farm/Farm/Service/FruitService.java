package com.Hanium.Farm.Farm.Service;

import com.Hanium.Farm.Farm.Domain.Fruit;
import com.Hanium.Farm.Farm.Domain.PeriodFruit;
import com.Hanium.Farm.Farm.Domain.RecommendFruit;
import com.Hanium.Farm.Farm.Repository.FruitRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public class FruitService {

    FruitRepositoryInterface fruitRepository;

    @Autowired
    public FruitService(FruitRepositoryInterface fruitRepository){
        this.fruitRepository = fruitRepository;
    }

    public Fruit getFruitInfo(String fruit){
        Fruit info = fruitRepository.getFruitInfo(fruit);

        return info;
    }

    public ArrayList<PeriodFruit> getPeriodFruits(int month){
        ArrayList<PeriodFruit> fruits = fruitRepository.getPeriodFruit(month);
        return fruits;
    }

    public ArrayList<RecommendFruit> getRecommendFruits(String[] nutrition){
        ArrayList<RecommendFruit> fruits = fruitRepository.getRecommendFruit(nutrition);
        return fruits;
    }

    public ArrayList<String> getFruitNames(){
        ArrayList<String> names = fruitRepository.getFruitNames();
        return names;
    }

    public String getHotFruits(){
        ArrayList<String> names = fruitRepository.getHotFruit();
        Iterator<String> it = names.iterator();
        String result = "";

        while(it.hasNext()){
            result += it.next() + " ";
        }

        return result;
    }

}
