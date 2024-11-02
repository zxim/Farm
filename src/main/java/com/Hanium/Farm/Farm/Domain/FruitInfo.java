package com.Hanium.Farm.Farm.Domain;

import java.util.ArrayList;
import java.util.Iterator;

public class FruitInfo {

    private ArrayList<Nutrition> infoList = new ArrayList<Nutrition>();
    private String f_name;

    public FruitInfo(ArrayList<Nutrition> infoList, String f_name){
        this.infoList = infoList;
        this.f_name = f_name;
    }

    public FruitInfo(String f_name){this.f_name = f_name;}

    // 메소드
    public void setInfoList(ArrayList<Nutrition> infoList) {
        this.infoList = infoList;
    }

    public ArrayList<Nutrition> getAllInfos() {
        return infoList;
    }

    public void add(Nutrition nut){infoList.add(nut);}

    public String getFruitName() {
        return f_name;
    }

    public Iterator<Nutrition> iterator(){
        return infoList.iterator();
    }
}
