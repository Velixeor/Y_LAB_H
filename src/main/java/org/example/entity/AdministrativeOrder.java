package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministrativeOrder {
    private Integer id;
    private String carBrand;
    private String carModel;
    private String username;
    private Service serviceType;
    private Status status;
    private Integer carID;
    private Integer userID;
    public AdministrativeOrder(
                               String carBrand,
                               String carModel,
                               String username,
                               Service serviceType,
                               Status status) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.username = username;
        this.serviceType = serviceType;
        this.status = status;
    }
}
