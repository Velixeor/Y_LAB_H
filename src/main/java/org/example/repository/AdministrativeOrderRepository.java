package org.example.repository;


import org.example.entity.AdministrativeOrder;
import org.example.entity.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AdministrativeOrderRepository {
    private List<AdministrativeOrder> administrativeOrders;

    public AdministrativeOrderRepository() {
        this.administrativeOrders = new ArrayList<>();
    }

    public void addAdministrativeOrder(AdministrativeOrder administrativeOrder) {
        administrativeOrders.add(administrativeOrder);
    }

    public List<AdministrativeOrder> getAllAdministrativeOrder() {
        return new ArrayList<>(administrativeOrders);
    }

    public boolean deleteOrderService(Integer id) {
        for (AdministrativeOrder administrativeOrder : administrativeOrders) {
            if (administrativeOrder.getId().equals(id)) {
                administrativeOrders.remove(administrativeOrder);
                return true;
            }
        }
        return false;
    }

    public Optional<AdministrativeOrder> getAdministrativeOrderById(Integer id) {
        for (AdministrativeOrder administrativeOrder : administrativeOrders) {
            if (administrativeOrder.getId().equals(id)) {
                return Optional.of(administrativeOrder);
            }
        }
        return Optional.empty();
    }

    public boolean updateAdministrativeOrder(AdministrativeOrder updatedAdministrativeOrder) {
        for (AdministrativeOrder administrativeOrder : administrativeOrders) {
            if (administrativeOrder.getId().equals(updatedAdministrativeOrder.getId())) {
                administrativeOrder.setCarBrand(updatedAdministrativeOrder.getCarBrand());
                administrativeOrder.setCarModel(updatedAdministrativeOrder.getCarModel());
                administrativeOrder.setUsername(updatedAdministrativeOrder.getUsername());
                administrativeOrder.setServiceType(updatedAdministrativeOrder.getServiceType());
                administrativeOrder.setStatus(updatedAdministrativeOrder.getStatus());
                return true;
            }
        }
        return false;
    }
    public List<AdministrativeOrder> searchAdministrativeOrdersByCustomerUsername(String username) {
        List<AdministrativeOrder> result = new ArrayList<>();
        for (AdministrativeOrder administrativeOrder : administrativeOrders) {
            if (administrativeOrder.getUsername().equalsIgnoreCase(username)) {
                result.add(administrativeOrder);
            }
        }
        return result;
    }


    public List<AdministrativeOrder> searchAdministrativeOrdersByStatus(Status status) {
        List<AdministrativeOrder> result = new ArrayList<>();
        for (AdministrativeOrder administrativeOrder : administrativeOrders) {
            if (administrativeOrder.getStatus().equals(status)) {
                result.add(administrativeOrder);
            }
        }
        return result;
    }
}
