package com.Hanium.Farm.Farm.Service;

import com.Hanium.Farm.Farm.Domain.ReviewInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;

@SpringBootTest
public class CommunityTest {

    @Autowired
    CommunityService communityService;

    @Test
    public void communityTest(){
        String fruit_name="참외";
        ArrayList<ReviewInfo> ar = communityService.getReviews(fruit_name);

        Iterator<ReviewInfo> it = ar.iterator();
        while(it.hasNext()){
            ReviewInfo review = it.next();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(review, ReviewInfo.class);
            System.out.println(json);
        }
    }

}
