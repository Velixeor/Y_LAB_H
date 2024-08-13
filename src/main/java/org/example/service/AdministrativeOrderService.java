package org.example.service;


import org.example.entity.AdministrativeOrder;
import org.example.entity.Status;
import org.example.repository.AdministrativeOrderRepository;

import java.util.List;
import java.util.Optional;


public class AdministrativeOrderService {

    private AdministrativeOrderRepository administrativeOrderRepository;

    public AdministrativeOrderService(AdministrativeOrderRepository administrativeOrderRepository) {
        this.administrativeOrderRepository = administrativeOrderRepository;

    }

    public void addAdministrativeOrder(AdministrativeOrder administrativeOrder) {
        administrativeOrderRepository.save(administrativeOrder);
    }

    public Optional<AdministrativeOrder> getAdministrativeOrderById(Integer id) {
        return administrativeOrderRepository.findById(id);
    }

    public List<AdministrativeOrder> getAllAdministrativeOrder() {
        return administrativeOrderRepository.findAll();
    }

    public AdministrativeOrder updateAdministrativeOrder(AdministrativeOrder updatedAdministrativeOrder) {
        return administrativeOrderRepository.update(updatedAdministrativeOrder);
    }

    public void deleteAdministrativeOrder(Integer id) {
         administrativeOrderRepository.deleteById(id);
    }

    public List<AdministrativeOrder> searchAdministrativeOrdersByCustomerUsername(String username) {
        return administrativeOrderRepository.searchAdministrativeOrdersByCustomerUsername(username);
    }

    public List<AdministrativeOrder> searchAdministrativeOrdersByStatus(Status status) {
        return administrativeOrderRepository.searchAdministrativeOrdersByStatus(status);
    }
}
