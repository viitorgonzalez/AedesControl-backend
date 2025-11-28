package com.aedescontrol.backend.mapper;

import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // CreateAddressDTO -> Address
    public Address toEntity(CreateAddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setZipCode(dto.getZipCode());
        address.setStatus(dto.getStatus());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        return address;
    }

    // Address -> AddressDTO
    public AddressDTO toDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setZipCode(address.getZipCode());
        dto.setStatus(address.getStatus());
        dto.setLatitude(address.getLatitude());
        dto.setLongitude(address.getLongitude());
        return dto;
    }

    // update DTO -> Entity
    public void updateEntity(Address entity, CreateAddressDTO dto) {
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setZipCode(dto.getZipCode());
        entity.setStatus(dto.getStatus());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
    }

}
