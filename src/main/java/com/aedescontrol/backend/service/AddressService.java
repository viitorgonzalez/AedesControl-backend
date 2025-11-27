package com.aedescontrol.backend.service;

import com.aedescontrol.backend.exception.ResourceNotFoundException;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressByIdOrThrow(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id)) ;
    }

    public List<Address> getAddressesByStatus(Address.Status status) {
        List<Address> addresses = addressRepository.findByStatus(status);

        if(addresses.isEmpty())
            throw new ResourceNotFoundException("Address not found with status " + status);
        return addresses;
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(long id) {
        Address address = getAddressByIdOrThrow(id);
        addressRepository.delete(address);
    }
}
