package org.example.entity;


public class AdministrativeOrder {
    private Integer id;
    private String carBrand;
    private String carModel;
    private String username;
    private Service serviceType;
    private Status status;

    public AdministrativeOrder(Integer id,
                               String carBrand,
                               String carModel,
                               String username,
                               Service serviceType,
                               Status status) {
        this.id = id;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.username = username;
        this.serviceType = serviceType;
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdministrativeOrder{" +
                "id=" + id +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", username='" + username + '\'' +
                ", serviceType=" + serviceType +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Service getServiceType() {
        return serviceType;
    }

    public void setServiceType(Service serviceType) {
        this.serviceType = serviceType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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
