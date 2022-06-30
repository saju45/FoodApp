package com.example.foodapp.Activity.Model;

public class FoodPostModel {

    String foodName;
    String postId;
    String postImg;
    String postedBy;
    String postDescription;
    String foodPrice;
    String foodNewPrice;
    String postAt;


    public FoodPostModel() {
    }

    public FoodPostModel(String foodName, String postId, String postImg, String postedBy, String postDescription, String foodPrice, String foodNewPrice, String postAt) {
        this.foodName = foodName;
        this.postId = postId;
        this.postImg = postImg;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.foodPrice = foodPrice;
        this.foodNewPrice = foodNewPrice;
        this.postAt = postAt;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodNewPrice() {
        return foodNewPrice;
    }

    public void setFoodNewPrice(String foodNewPrice) {
        this.foodNewPrice = foodNewPrice;
    }

    public String getPostAt() {
        return postAt;
    }

    public void setPostAt(String postAt) {
        this.postAt = postAt;
    }
}
