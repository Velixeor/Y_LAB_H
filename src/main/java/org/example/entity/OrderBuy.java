package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBuy {
    private Integer id;
    private Integer carID;
    private Integer userID;
    private Status status;
    public OrderBuy(Integer carID, Integer userID, Status status) {
        this.carID = carID;
        this.userID = userID;
        this.status = status;
    }
}
