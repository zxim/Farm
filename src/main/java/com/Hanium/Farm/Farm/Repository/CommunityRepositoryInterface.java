package com.Hanium.Farm.Farm.Repository;

import com.Hanium.Farm.Farm.Domain.Review;
import com.Hanium.Farm.Farm.Domain.ReviewInfo;
import com.Hanium.Farm.Farm.Domain.SingleComment;

import java.util.ArrayList;

public interface CommunityRepositoryInterface {

    // 게시물 업로드
    public String upload_review(Review review);

    public ArrayList<Review> getReviewInfo(String fruit_name);

    public ArrayList<Review> getUserReviews(String user_id);

    public String updateReview(Review review, String pre_name);

    public String deleteReview(Review review);

    public ArrayList<SingleComment> getComments(String review_id);

    public boolean insertComment(SingleComment comment);

    public boolean removeComment(SingleComment comment);

    public boolean insertGood(String review_id, String user_id);

    public boolean deleteGood(String review_id, String user_id);
}
