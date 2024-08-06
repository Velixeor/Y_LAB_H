package org.example.service;


import org.example.entity.AdministrativeOrder;
import org.example.entity.Status;
import org.example.repository.AdministrativeOrderRepository;

import java.util.List;
import java.util.Optional;


public class AdministrativeOrderService {

    private AdministrativeOrderRepository administrativeOrderRepository;
    private int id;
    public AdministrativeOrderService(AdministrativeOrderRepository administrativeOrderRepository) {
        this.administrativeOrderRepository = administrativeOrderRepository;
        id=0;
    }

    public void addAdministrativeOrder(AdministrativeOrder administrativeOrder) {
        administrativeOrder.setId(id);
        id++;
        administrativeOrderRepository.addAdministrativeOrder(administrativeOrder);
    }

    public Optional<AdministrativeOrder> getAdministrativeOrderById(Integer id) {
        return administrativeOrderRepository.getAdministrativeOrderById(id);
    }

    public List<AdministrativeOrder> getAllAdministrativeOrder() {
        return administrativeOrderRepository.getAllAdministrativeOrder();
    }

    public boolean updateAdministrativeOrder(AdministrativeOrder updatedAdministrativeOrder) {
        return administrativeOrderRepository.updateAdministrativeOrder(updatedAdministrativeOrder);
    }

    public boolean deleteAdministrativeOrder(Integer id) {
        return administrativeOrderRepository.deleteOrderService(id);
    }

    public List<AdministrativeOrder> searchAdministrativeOrdersByCustomerUsername(String username) {
        return administrativeOrderRepository.searchAdministrativeOrdersByCustomerUsername(username);
    }

    public List<AdministrativeOrder> searchAdministrativeOrdersByStatus(Status status) {
        return administrativeOrderRepository.searchAdministrativeOrdersByStatus(status);
    }
}
