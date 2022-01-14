package com.example.transporteationtask.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transport")
public class Transport {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "points")
    private int points;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "building_time")
    private long buildingTime;

    @ColumnInfo(name = "build_points")
    private int buildPoints;

    @ColumnInfo(name = "created_at")
    private long createdAt;

    @ColumnInfo(name = "updated_at")
    private long updatedAt;

    public Transport(int id, String name, String price, String type, String imageUrl,
                     int points, String state, long buildingTime, int buildPoints) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.imageUrl = imageUrl;
        this.points = points;
        this.state = state;
        this.buildingTime = buildingTime;
        this.buildPoints = buildPoints;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getBuildingTime() {
        return buildingTime;
    }

    public void setBuildingTime(long buildingTime) {
        this.buildingTime = buildingTime;
    }

    public int getBuildPoints() {
        return buildPoints;
    }

    public void setBuildPoints(int buildPoints) {
        this.buildPoints = buildPoints;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
