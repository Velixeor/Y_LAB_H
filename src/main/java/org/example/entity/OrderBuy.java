package org.example.entity;

public class OrderBuy {
    private Integer id;
    private Integer carID;
    private Integer userID;
    private Status status;


    public OrderBuy(Integer id, Integer carID, Integer userID, Status status) {
        this.id = id;
        this.carID = carID;
        this.userID = userID;
        this.status = status;
    }

    public OrderBuy(Integer carID, Integer userID, Status status) {
        this.carID = carID;
        this.userID = userID;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderBuy{" +
                "id=" + id +
                ", carID=" + carID +
                ", userID=" + userID +
                ", status=" + status +
                '}';
    }
}
