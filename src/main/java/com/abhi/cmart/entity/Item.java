package com.abhi.cmart.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="brand")
    private String brand;

    @Column(name="height")
    private int height;

    @Column(name="width")
    private int width;

    @Column(name="depth")
    private int depth;

    @Column(name="weight")
    private int weight;

    @Column(name="colour")
    private String colour;

    @Column(name="warranty")
    private int warranty;

    @Column(name="material")
    private String material;

    @Column(name="cost")
    private int cost;

    @Column(name="imageURL")
    private String imageURL;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private Category category;

    public Item() {
    }

    public Item(int id, String name, String brand, int height, int width, int depth, int weight, String colour, int warranty, String material, int cost, String imageURL, Category category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.weight = weight;
        this.colour = colour;
        this.warranty = warranty;
        this.material = material;
        this.cost = cost;
        this.imageURL = imageURL;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", depth=" + depth +
                ", weight=" + weight +
                ", colour='" + colour + '\'' +
                ", warranty=" + warranty +
                ", material='" + material + '\'' +
                ", cost=" + cost +
                ", imageURL='" + imageURL + '\'' +
                ", category=" + category +
                '}';
    }
}