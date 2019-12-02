package com.example.foodsavierapp;

/**
 * Card view object
 */
public class ExampleItemLog {
    private int cardImage;
    private String cardFoodName;
    private String cardStatus;
    private String cardExpiredDate;
    private String cardFoodQuantity;

    public ExampleItemLog(int image, String foodName, String status, String expiredDate, String quantity) {
        cardImage = image;
        cardFoodName = foodName;
        cardStatus = status;
        cardExpiredDate = expiredDate;
        cardFoodQuantity = quantity;
    }

    public int getCardImage() {
        return cardImage;
    }

    public String getCardFoodName() {
        return cardFoodName;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public String getCardExpiredDate() {
        return cardExpiredDate;
    }

    public String getCardFoodQuantity() { return cardFoodQuantity; }


}
