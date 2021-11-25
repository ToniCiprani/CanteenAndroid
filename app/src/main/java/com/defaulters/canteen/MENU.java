package com.defaulters.canteen;

public class MENU {


    String FoodCategory,FoodName,FoodPrice,rating,FoodId,count;


    public MENU()
    {

    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFoodCategory() {
        return FoodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        FoodCategory = foodCategory;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        FoodPrice = foodPrice;
    }
}
