package com.Hanium.Farm.Farm.Service;

import com.Hanium.Farm.Farm.Domain.Review;
import com.Hanium.Farm.Farm.Domain.ReviewInfo;
import com.Hanium.Farm.Farm.Domain.SingleComment;
import com.Hanium.Farm.Farm.Repository.CommunityRepositoryInterface;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CommunityService {
    CommunityRepositoryInterface communityRepository;
    String filePath = "";

    @Autowired
    public CommunityService(CommunityRepositoryInterface communityRepository){
        this.communityRepository = communityRepository;
    }

    public String registReview(MultipartFile image, String review, String method, String fileName){
        String result = "false";

        try{
            if(!image.isEmpty()) { // Image처리 Image가 존재할 경우 Image를 경로에 저장한다.
                byte[] imageData = image.getBytes();
                byte[] decodedBytes = Base64.getDecoder().decode(image.getOriginalFilename()); // Base64로부터 디코딩 함
                String originalImageName = new String(decodedBytes, StandardCharsets.UTF_8); // UTF-8로부터 디코딩함
                String totalPath = filePath + originalImageName; // Image파일 이름은 과일이름_사용자ID_리뷰시간.jpg
                FileOutputStream fos = new FileOutputStream(totalPath); // Image이름을 정하여 지정된 경로에 이미지 파일을 저장한다.
                System.out.println("File Name : " + originalImageName);
                fos.write(imageData);
            }
            Gson gson = new Gson();
            Review review_content = gson.fromJson(review, Review.class); // json으로부터 Review객체 생성
            System.out.println("이미지 업로드 성공");
            if(method.equals("upload")) { // 게시물 등록 요청 시 DB작업
                result = communityRepository.upload_review(review_content);
            }
            else if(method.equals("update")){ // 게시물 수정 요청 시 DB작업
                result = communityRepository.updateReview(review_content, fileName.split("_")[0].toString());
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return result;
    }

    // ArrayList형태로 Review정보와 Image데이터를 전달한다.
    public ArrayList<ReviewInfo> getReviews(String fruit_name) { // Review정보와 image Byte데이터를 Base64로 String형식으로 만들어 반환
        ArrayList<Review> reviews = communityRepository.getReviewInfo(fruit_name);
        ArrayList<ReviewInfo> result = new ArrayList<ReviewInfo>();

        Iterator<Review> it = reviews.iterator();
        while(it.hasNext()){ // 리뷰 정보에 담긴 fruit_name, user_id, review_time을 합하여 이미지 이름 생성
            Review review = it.next();
            String[] temp = review.getReview_time().split(" ");
            String review_time = temp[0] + "-" + temp[1].replace(":", "-");

            String fileName = review.getFruit_name() + "_" + review.getUser_id() + "_" + review_time + ".jpg";
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(filePath + fileName));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                result.add(new ReviewInfo(review, base64Image)); // fileName을 image로
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    // Review객체와 Image정보를 바탕을로 ReviewInfo를 Controller에 전달한다.
    public ArrayList<ReviewInfo> getUserReviews(String user_id){
        ArrayList<Review> reviews = communityRepository.getUserReviews(user_id);
        if(reviews == null){
            System.out.println("null");
        }else
            System.out.println("not null");
        Iterator<Review> it = reviews.iterator();
        ArrayList<ReviewInfo> result = new ArrayList<>();

        while(it.hasNext()) {
            Review review = it.next();
            String[] temp = review.getReview_time().split(" ");
            String review_time = temp[0] + "-" + temp[1].replace(":", "-");

            String fileName = review.getFruit_name() + "_" + review.getUser_id() + "_" + review_time + ".jpg";
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(filePath + fileName));
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                result.add(new ReviewInfo(review, base64Image)); // fileName을 image로
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public String deleteReview(Review review){
        review.setReview_time(review.getReview_time().replace(" ", "-"));
        review.setReview_time(review.getReview_time().replace(":", "-"));
        System.out.println(review.getUser_id());
        String result = communityRepository.deleteReview(review);

        String filename = review.getFruit_name() + "_" + review.getUser_id() + "_" + review.getReview_time() + ".jpg";
        System.out.println("삭제" + result);
        try{
            if(result.equals("true")) {
                Path path = Paths.get(filePath + filename);
                Files.delete(path);
            }else
                System.out.println("DB삭제 실패");
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public String deleteImage(String fileName) {
        String flag = "false";

        // 경로 설정 및 이미지 파일 삭제
        Path path = Paths.get(filePath + fileName);
        try {
            Files.delete(path);
        }catch(IOException e){
            e.printStackTrace();
        }

        return flag;
    }

    public String updateReview(Review review, String fileName)  {
        String result;

        String prevPath = filePath + fileName;
        String temp = review.getReview_time().replace(" ", "-").replace(":", "-");
        String name = review.getFruit_name() + "_" + review.getUser_id() + "_" + temp + ".jpg";
        String totalPath = filePath + name;
        try {
            Files.move(Path.of(prevPath), Path.of(totalPath));
        }catch(Exception e){
            e.printStackTrace();
        }

        result = communityRepository.updateReview(review, fileName.split("_")[0]);

        return result;
    }

    public ArrayList<SingleComment> getComments(String review_id){
        ArrayList<SingleComment> comments = communityRepository.getComments(review_id);

        return comments;
    }

    public boolean insertComment(SingleComment comment){
        boolean result = false;

        result = communityRepository.insertComment(comment);

        return result;
    }

    public boolean removeComment(SingleComment comment){
        boolean result = communityRepository.removeComment(comment);

        return result;
    }

    public boolean insertGood(String review_id, String user_id){
        return communityRepository.insertGood(review_id, user_id);
    }

    public boolean deleteGood(String review_id, String user_id){
        return communityRepository.deleteGood(review_id, user_id);
    }

}
