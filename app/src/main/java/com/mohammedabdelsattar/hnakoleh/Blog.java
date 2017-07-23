package com.mohammedabdelsattar.hnakoleh;

/**
 * Created by mabdelsattar on 7/8/2017.
 */

public class Blog {
    public String title,desc,image;
    public double price;
    public Boolean delivery;

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public   Blog(){}
    public Blog(String title, String desc, String image, double price,Boolean delivery) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.price = price;
        this.delivery=delivery;
    }

}
