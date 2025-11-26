package com.aedescontrol.backend.controller;

import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.mapper.AddressMapper;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressMapper mapper;

    @GetMapping()
    public List<AddressDTO> getAddressById() {
        return addressService.getAllAddresses()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Long id) {
        return mapper.toDTO(addressService.getAddressByIdOrThrow(id));
    }

    @GetMapping("/status/{status}")
    public List<AddressDTO> getAddressesByStatus(@PathVariable Address.Status status) {
        return addressService.getAddressesByStatus(status)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
