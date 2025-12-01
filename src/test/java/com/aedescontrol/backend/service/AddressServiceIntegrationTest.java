package com.aedescontrol.backend.service;

import com.aedescontrol.backend.config.BaseIntegrationTest;
import com.aedescontrol.backend.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AddressServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    AddressService service;

    @Autowired
    AddressRepository repo;

    @Test
    void shouldCreateAddress() {
    }

}
