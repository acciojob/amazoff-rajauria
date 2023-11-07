package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap <String,Order> orderDB ;
    HashMap <String,DeliveryPartner> partnerDB;

    HashMap <String,String> orderPartnerPair ;

    HashMap <String, ArrayList<String>> partnerOrderList ;

    public OrderRepository (){
        this.orderDB = new HashMap<>();
        this.partnerDB = new HashMap<>();
        this.orderPartnerPair = new HashMap<>();
        this.partnerOrderList = new HashMap<>();
    }


    public void addOrderToDB (Order obj){
        orderDB.put(obj.getId(),obj);
    }

    public void addPartnerToDB(String partnerId) {
        partnerDB.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if (orderDB.containsKey(orderId) && partnerDB.containsKey(partnerId)){
            orderPartnerPair.put(orderId,partnerId);
            ArrayList<String> list = new ArrayList<>();
            if (partnerOrderList.containsKey(partnerId)){
                list = partnerOrderList.get(partnerId);
            }
                list.add (orderId);
                partnerOrderList.put(partnerId,list);
                DeliveryPartner obj =  partnerDB.get(partnerId);
                obj.setNumberOfOrders(list.size());
        }
    }

    public Order getOrderByID (String orderID){
        return orderDB.get(orderID);
    }

    public DeliveryPartner getPartnerByID(String partnerId) {
        return partnerDB.get(partnerId);
    }

    public int getTotalOrdersByID(String partnerId) {
        DeliveryPartner obj = partnerDB.get(partnerId);
        return obj.getNumberOfOrders();
    }

    public List<String> getOrderList(String partnerId) {
        return partnerOrderList.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderDB.keySet());
    }


    public Integer getCountOfUnassignedOrders() {
        return orderDB.size() - orderPartnerPair.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {
        int count = 0;
        List<String> list = partnerOrderList.get(partnerId);
        for (String orderID : list){
            Order obj = orderDB.get(orderID);
            int realTime = obj.getDeliveryTime();
            if (time < realTime){
                count++;
            }
        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastOrderTime = 0;
        ArrayList<String> orderIDs = partnerOrderList.get(partnerId);
        for (String orderID : orderIDs){
            Order obj = orderDB.get(orderID);
            lastOrderTime = Math.max (lastOrderTime,obj.getDeliveryTime());
        }
        return lastOrderTime;
    }

    public void deleteOrderById(String orderId) {
        orderDB.remove(orderId);
        if (orderPartnerPair.containsKey(orderId)){
            String partnerID = orderPartnerPair.get(orderId);
            orderPartnerPair.remove(orderId);
            partnerOrderList.get(partnerID).remove(orderId);
            partnerDB.get(partnerID).setNumberOfOrders(partnerOrderList.get(partnerID).size());
        }
    }

    public void deletePartnerById(String partnerId) {
        partnerDB.remove(partnerId);
        List<String> orderIDs = partnerOrderList.get(partnerId);
        for (String orderID : orderIDs){
            orderPartnerPair.remove(orderID);
        }
        partnerOrderList.remove(partnerId);
    }


}
