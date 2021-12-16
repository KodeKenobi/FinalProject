package com.synerdy.kodekenobi.Model;

public class Products {
    private String category, description, image, pid, date, pname, price, time, productState;

    public Products() {

    }

    public Products(String category, String description, String image, String pid, String date, String pname, String price, String time, String productState) {
        this.category = category;
        this.description = description;
        this.image = image;
        this.pid = pid;
        this.date = date;
        this.pname = pname;
        this.price = price;
        this.time = time;
        this.productState = productState;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }
}