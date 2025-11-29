package com.aedescontrol.backend.service;

import com.aedescontrol.backend.config.BaseIntegrationTest;
import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AddressServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    AddressService service;

    @Autowired
    AddressRepository repo;

    @Test
    void shouldCreateAddress() {
        CreateAddressDTO dto = new CreateAddressDTO();
        dto.setStreet("Rua A");
        dto.setCity("Cidade");
        dto.setZipCode("12345");
        dto.setStatus(Address.Status.LIVRE);

        AddressDTO saved = service.saveAddress(dto);

        assertNotNull(saved.getId());
    }

}
