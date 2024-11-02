package com.Hanium.Farm.Farm.Domain;

import java.io.Serializable;

public class ReviewInfo implements Serializable {
    private Review review;
    private String image;

    public Review getReview() {
        return review;
    }

    public String getImage() {
        return image;
    }



    public ReviewInfo(Review review, String image){
        this.review = review;
        this.image = image;
    }

}
