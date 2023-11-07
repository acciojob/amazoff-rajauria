package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        String str[] = deliveryTime.split(":");
        int a = Integer.parseInt(str[0]);
        int b = Integer.parseInt(str[1]);
        this.deliveryTime = a*60+b;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
