package com.defaulters.canteen;

public class ORDER {


    String OrderID,OrderPrice,OrderName,OrderQuantity,UserEmail,OrderTimestamp,OrderStatus,MenuID,rateFlag;


    ORDER()
    {

    }

    public String getRateFlag() {
        return rateFlag;
    }

    public void setRateFlag(String rateFlag) {
        this.rateFlag = rateFlag;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getOrderTimestamp() {
        return OrderTimestamp;
    }

    public void setOrderTimestamp(String orderTimestamp) {
        OrderTimestamp = orderTimestamp;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        OrderQuantity = orderQuantity;
    }
}
