package com.example.db2medicalinsurance;

import java.util.Date;

public class ItemInfo {
    private String description;
    private String name;
    private Double price;
    private Double discount;
    private Date start_time;
    private Date end_time;
    public ItemInfo() {}


    public Date getStart_time() {
        return start_time;
    }

    public ItemInfo(String description, String name, Double price, Double discount, Date start_time, Date end_time) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}

