package com.Hanium.Farm.Farm.Repository;

import com.Hanium.Farm.Farm.Domain.Review;
import com.Hanium.Farm.Farm.Domain.SingleComment;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CommunityRepository implements CommunityRepositoryInterface{

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    public CommunityRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Review게시글 등록
    @Override
    public String upload_review(Review review) {
        String fruit_name = review.getFruit_name();
        String review_time = review.getReview_time();
        String user_id = review.getUser_id();
        String content = review.getContent();
        String flavor = review.getFlavor();

        int cnt;
        String result = "false";

        if(flavor != null){
            cnt = jdbcTemplate.update("INSERT INTO review(fruit_name, review_time, id, content, flavor)" +
                    "VALUES (?, ?, ?, ?, ?);", fruit_name, review_time, user_id, content, flavor);
        }else{
            cnt = jdbcTemplate.update("INSERT INTO review(fruit_name, review_time, id, content, flavor)" +
                    "VALUES (?, ?, ?, ?, ?);", fruit_name, review_time, user_id, content, "");
        }

        if(cnt > 0)
            result = "true";
        else
            result = "false";

        return result;
    }

    // 과일 이름 별 전체 게시물 및 좋아요 받아오기
    @Override
    public ArrayList<Review> getReviewInfo(String fruit_name){;
        List<Review> result = jdbcTemplate.query("SELECT * FROM review LEFT JOIN good ON review.review_id=good.review_id WHERE fruit_name=? ORDER BY review_time DESC", reviewMapper(), fruit_name);
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.addAll(result);

        return reviews;
    }

    private RowMapper<Review> reviewMapper(){
        return (rs, rowNum) ->{
            String fruit_name = rs.getString("fruit_name");
            String review_time = rs.getString("review_time");
            String user_id = rs.getString("id");
            String content = rs.getString("content");
            String flavor = rs.getString("flavor");
            String review_id = rs.getString("review_id");
            String comment_user_id = rs.getString("user_id");
            String good = rs.getString("user_id");
            if(content != null) // 내용이 있는 경우
                if(good == null)
                    return new Review(fruit_name, review_time, user_id, content, flavor, review_id, "");
                else
                    return new Review(fruit_name, review_time, user_id, content, flavor, review_id, good);
            else // 내용이 없는 경우
                if(good == null)
                    return new Review(fruit_name, review_time, user_id, null, flavor, review_id, "");
                else
                    return new Review(fruit_name, review_time, user_id, null, flavor, review_id, good);
        };
    }

    @Override
    public ArrayList<Review> getUserReviews(String user_id){
        List<Review> result = jdbcTemplate.query("SELECT * FROM review LEFT JOIN good ON review.review_id=good.review_id WHERE id=?", userReviewMapper(), user_id);
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.addAll(result);
        return reviews;
    }

    private RowMapper<Review> userReviewMapper(){
        return (rs, rowNum) ->{
            String fruit_name = rs.getString("fruit_name");
            String  review_time = rs.getString("review_time");
            String user_id = rs.getString("id");
            String content = rs.getString("content");
            String flavor = rs.getString("flavor");
            String review_id = rs.getString("review_id");
            String good = rs.getString("user_id");

            if(content != null)
                if(good == null)
                    return new Review(fruit_name, review_time, user_id, content, flavor, review_id, "");
                else
                    return new Review(fruit_name, review_time, user_id, content, flavor, review_id, good);
            else
                if(good == null)
                    return new Review(fruit_name, review_time, user_id, null, flavor, review_id, "");
                else
                    return new Review(fruit_name, review_time, user_id, null, flavor, review_id, good);
        };
    }

    // 게시물 내용 수정
    @Override
    public String updateReview(Review review, String pre_name) {
        System.out.println(pre_name);
        int cnt = jdbcTemplate.update("UPDATE review SET fruit_name=?, content=?, flavor=? WHERE fruit_name=? and id=? and review_time=?",
                review.getFruit_name(), review.getContent(), review.getFlavor(), pre_name, review.getUser_id(), review.getReview_time());
        String flag = "false";
        if(cnt > 0)
            flag = "true";
        return flag;
    }

    // 게시물 내용 삭제
    @Override
    public String deleteReview(Review review) {
        int cnt = jdbcTemplate.update("DELETE FROM review WHERE fruit_name=? and id=? and review_time=?", review.getFruit_name(), review.getUser_id(), review.getReview_time());
        jdbcTemplate.update("DELETE FROM good WHERE review_id=? and user_id=?", review.getReview_id(), review.getUser_id());
        log.info(cnt + "");
        String result = "false";
        if(cnt > 0)
            result = "true";
        return result;
    }

    // 게시물에 대한 댓글 정보들을 모두 가져온다.
    @Override
    public ArrayList<SingleComment> getComments(String review_id){
        ArrayList<SingleComment> comments = new ArrayList<>();

        List<SingleComment> temp = jdbcTemplate.query("SELECT * FROM comment WHERE review_id=?", CommentsMapper(), review_id);
        comments.addAll(temp);

        return comments;
    }

    private RowMapper<SingleComment> CommentsMapper() {
        return (rs, rowNum) -> {
            return new SingleComment(rs.getString("id"), rs.getString("comment"), rs.getString("post_time"), rs.getString("review_id"));
        };
    }


    @Override
    public boolean insertComment(SingleComment comment){
        boolean result = false;

        System.out.println(comment.getReview_id() + comment.getDate() + comment.getUser_id() + comment.getComment());
        // insert구문 성공 시 result = true구문
        jdbcTemplate.update("INSERT INTO comment(review_id, post_time, id, comment) " +
                "VALUES(?, ?, ?, ?)", comment.getReview_id(), comment.getDate(), comment.getUser_id(), comment.getComment());

        return result;
    }


    @Override
    public boolean removeComment(SingleComment comment){
        int cnt;
        boolean result;

        cnt = jdbcTemplate.update("DELETE FROM comment WHERE review_id=? and comment=? and user_id=?", comment.getReview_id(), comment.getComment(), comment.getUser_id());
        if(cnt > 0)
            result = true;
        else
            result = false;

        return result;
    }

    @Override
    public boolean insertGood(String review_id, String user_id){
        int cnt = jdbcTemplate.update("INSERT INTO good VALUES(?, ?)", review_id, user_id);
        if(cnt > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean deleteGood(String review_id, String user_id){
        int cnt = jdbcTemplate.update("DELETE FROM good WHERE review_id=? and user_id=?", review_id, user_id);

        if(cnt > 0)
            return true;
        else
            return false;
    }

}