package com.g7.wanbao.csmuse;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;

/**
 * Created by XNS on 2016/4/10.
 */
public class Order {
    private JSONObject orderDetails;

    private String orderNo;
    private String externalOrderNo;
    private int productSN;
    private String itemNo;
    private String itemName;
    private String styleA;
    private String styleB;
    private int quantity;
    private int price;
    private int amount;
    private String orderName;
    private String orderAddress;
    private String orderEmail;
    private String orderPhone;
    private String consigneeName;
    private String consigneeAddress;
    private String consigneeEmail;
    private String consigneePhone;
    private String deliverTime;
    private int result;
    private String param;
    private Date sendDate;
    private byte paymentResult;
    private byte dealWithResult;

    public Order(JSONObject _orderDetails) {
        orderDetails = _orderDetails;
        orderNo = orderDetails.optString("OrderNo");
        externalOrderNo = orderDetails.optString("ExternalOrderNo");
        productSN = orderDetails.optInt("ProductSN");
        itemNo = orderDetails.optString("ItemNo");
        itemName = orderDetails.optString("ItemName");
        styleA = orderDetails.optString("StyleA");
        styleB = orderDetails.optString("StyleB");
        quantity = orderDetails.optInt("Quantity");
        price = orderDetails.optInt("Price");
        amount = orderDetails.optInt("Amount");
        orderName = orderDetails.optString("OrderName");
        orderAddress = orderDetails.optString("OrderAddress");
        orderEmail = orderDetails.optString("OrderEmail");
        orderPhone = orderDetails.optString("OrderPhone");
        consigneeName = orderDetails.optString("ConsigneeName");
        consigneeAddress = orderDetails.optString("ConsigneeAddress");
        consigneeEmail = orderDetails.optString("ConsigneeEmail");
        consigneePhone = orderDetails.optString("ConsigneePhone");
        deliverTime = orderDetails.optString("DeliverTime");
        result = orderDetails.optInt("Result");
        param = orderDetails.optString("Param");
        try {
            sendDate = DateFormat.getDateInstance().parse(orderDetails.optString("SendDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentResult = (byte)orderDetails.optInt("PaymentResult");
        dealWithResult = (byte)orderDetails.optInt("DealWithResult");
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getExternalOrderNo() {
        return externalOrderNo;
    }

    public int getProductSN() {
        return productSN;
    }

    public String getItemNo() {
        return itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public String getStyleA() {
        return styleA;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStyleB() {
        return styleB;
    }

    public int getPrice() { return price; }

    public int getAmount() {
        return amount;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public String getOrderEmail() {
        return orderEmail;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public String getConsigneeEmail() {
        return consigneeEmail;
    }

    public String getConsigneePhone() { return consigneePhone; }

    public String getDeliverTime() {
        return deliverTime;
    }

    public String getParam() {
        return param;
    }

    public int getResult() {
        return result;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public byte getPaymentResult() {
        return paymentResult;
    }

    public byte getDealWithResult() {
        return dealWithResult;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", externalOrderNo='" + externalOrderNo + '\'' +
                ", productSN=" + productSN +
                ", itemNo='" + itemNo + '\'' +
                ", itemName='" + itemName + '\'' +
                ", styleA='" + styleA + '\'' +
                ", styleB='" + styleB + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", amount=" + amount +
                ", orderName='" + orderName + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderEmail='" + orderEmail + '\'' +
                ", orderPhone='" + orderPhone + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", consigneeAddress='" + consigneeAddress + '\'' +
                ", consigneeEmail='" + consigneeEmail + '\'' +
                ", consigneePhone='" + consigneePhone + '\'' +
                ", deliverTime='" + deliverTime + '\'' +
                ", result=" + result +
                ", param='" + param + '\'' +
                ", sendDate=" + sendDate +
                ", paymentResult=" + paymentResult +
                ", dealWithResult=" + dealWithResult +
                '}';
    }
}