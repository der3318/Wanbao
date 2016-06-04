package com.g7.wanbao.csmuse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by XNS on 2016/4/10.
 */
public class Product {
    private JSONObject json;
    private JSONObject productDetails;

    private int productSN;
    private String productTitle;
    private String productNo;
    private int sellPrice;
    private int sellPriceCNY;
    private int productQuantity;
    private String productIntroduction;
    private String styleTitleA;
    private String styleTitleB;
    private ArrayList<String> productPhoto;
    private JSONArray style;

    public Product(JSONObject _json) {
        try {
            json = _json;

            productDetails = json.getJSONArray("Product").getJSONObject(0);

            productSN = productDetails.getInt("ProductSN");
            productTitle = productDetails.getString("ProductTitle");
            productNo = productDetails.getString("ProductNo");

            sellPrice = productDetails.getInt("SellPrice");
            sellPriceCNY = productDetails.getInt("SellPriceCNY");

            productQuantity = productDetails.getInt("ProductQuantity");

            productIntroduction = productDetails.getString("ProductIntroduction");

            styleTitleA = productDetails.getString("StyleTitleA");
            styleTitleB = productDetails.getString("StyleTitleB");

            productPhoto = new ArrayList<String>();
            for(int i = 0; i < 8; i++) {
                productPhoto.add(productDetails.getString("ProductPhoto" + (i + 1)));
            }

            style = productDetails.getJSONArray("Style");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getProductSN() {
        return productSN;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductNo() {
        return productNo;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getSellPriceCNY() {
        return sellPriceCNY;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductIntroduction() {
        return productIntroduction;
    }

    public String getStyleTitleB() {
        return styleTitleB;
    }

    public String getStyleTitleA() {
        return styleTitleA;
    }

    public ArrayList<String> getProductPhoto() {
        return productPhoto;
    }

    @Override
    public String toString() {
        return "Product{" +
                " productSN=" + productSN +
                ", productTitle='" + productTitle + '\'' +
                ", productNo='" + productNo + '\'' +
                ", sellPrice=" + sellPrice +
                ", sellPriceCNY=" + sellPriceCNY +
                ", productQuantity=" + productQuantity +
                ", productIntroduction='" + productIntroduction + '\'' +
                ", styleTitleA='" + styleTitleA + '\'' +
                ", styleTitleB='" + styleTitleB + '\'' +
                ", productPhoto=" + productPhoto +
                ", style=" + style +
                '}';
    }

    public JSONArray getStyle() {
        return style;
    }

    public void printJson() {
        System.out.println(json.toString());
    }
}