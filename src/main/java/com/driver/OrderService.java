package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository = new OrderRepository();



    public void addOrderToDB (Order obj){
        orderRepository.addOrderToDB (obj);
    }


    public void addPartner(String partnerId) {
        orderRepository.addPartnerToDB (partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair (orderId,partnerId);
    }

    public Order getOrderByID (String orderID){
        return orderRepository.getOrderByID (orderID);
    }

    public DeliveryPartner getPartnerByID(String partnerId) {
       return orderRepository.getPartnerByID (partnerId);
    }

    public int getTotalOrderByID(String partnerId) {
        return orderRepository.getTotalOrdersByID (partnerId);
    }

    public List<String> getOrderList(String partnerId) {
        return orderRepository.getOrderList (partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders ();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String [] timeArray = time.split(":");
        int a = Integer.parseInt(timeArray[0]);
        int b = Integer.parseInt(timeArray[1]);
        int newTime  = a*60+b;
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId( newTime, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time1 = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        String HH = String.valueOf(time1/60);
        String MM = String.valueOf(time1%60);
        if(HH.length()<2){
            HH ="0"+HH;
        }
        if(MM.length()<2){
            MM ="0"+MM;
        }
        return HH+":"+MM;
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }
}
