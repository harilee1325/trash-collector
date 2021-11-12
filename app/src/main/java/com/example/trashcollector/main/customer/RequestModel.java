package com.example.trashcollector.main.customer;

import java.util.List;

public class RequestModel {

    private String date;
    private String locationName;
    private String lat;
    private String lng;
    private String status;
    private String driverName;
    private String userName;
    private String userId;
    private String driverId;
    private String id;
    private List<String> commentDriver;
    private List<String> commentOfficer;

    public List<String> getCommentDriver() {
        return commentDriver;
    }

    public void setCommentDriver(List<String> commentDriver) {
        this.commentDriver = commentDriver;
    }

    public List<String> getCommentOfficer() {
        return commentOfficer;
    }

    public void setCommentOfficer(List<String> commentOfficer) {
        this.commentOfficer = commentOfficer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
