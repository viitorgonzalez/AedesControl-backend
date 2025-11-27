package com.aedescontrol.backend.service;

import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.exception.ResourceNotFoundException;
import com.aedescontrol.backend.mapper.AddressMapper;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper mapper;

    public AddressService(AddressRepository addressRepository, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll(); // to-do: Pageable
    }

    public Address getAddressByIdOrThrow(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id));
    }

    public List<Address> getAddressesByStatus(Address.Status status) {
        List<Address> addresses = addressRepository.findByStatus(status);

        if (addresses.isEmpty())
            throw new ResourceNotFoundException("Address not found with status " + status);
        return addresses;
    }

    public Address saveAddress(CreateAddressDTO dto) {
        Address address = mapper.toEntity(dto);
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        Address address = getAddressByIdOrThrow(id);
        addressRepository.delete(address);
    }
}
