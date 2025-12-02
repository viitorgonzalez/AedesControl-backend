package com.aedescontrol.backend.service;

import com.aedescontrol.backend.dto.AddressDTO;
import com.aedescontrol.backend.dto.CreateAddressDTO;
import com.aedescontrol.backend.exception.ResourceNotFoundException;
import com.aedescontrol.backend.mapper.ObjectMapper;
import com.aedescontrol.backend.model.Address;
import com.aedescontrol.backend.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository, ObjectMapper mapper) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDTO> getAllAddresses() {
        log.debug("Procurando todos os endereços");
        return ObjectMapper.parseListObjects(addressRepository.findAll(), AddressDTO.class); // to-do: Pageable
    }

    public AddressDTO getAddressByIdOrThrow(Long id) {
        log.debug("Procurando endereço no banco, id={}", id);
        return addressRepository.findById(id).map(address -> {
                    log.info("Endereço encontrado: {}", id);
                    return ObjectMapper.parseObject(address, AddressDTO.class);
                })
                .orElseThrow(() -> {
                    log.warn("Endereço não encontrado: {}", id);
                    return new ResourceNotFoundException("Endereço não encontrado com id: " + id);
                });

    }

    public List<AddressDTO> getAddressesByStatus(Address.Status status) {
        log.debug("Procurando endereço no banco, status={}", status);
        List<Address> addresses = addressRepository.findByStatus(status);

        if (addresses.isEmpty()) {
            log.warn("Endereço não encontrado com status: {}", status);
            throw new ResourceNotFoundException("Endereço não encontrado com status: " + status);
        }

        log.debug("Foram encontrados {} endereços com status {}", addresses.size(), status);

        return ObjectMapper.parseListObjects(addresses, AddressDTO.class);
    }

    public AddressDTO saveAddress(CreateAddressDTO dto) {
        log.debug("Salvando endereço: body={}", dto);
        Address address = ObjectMapper.parseObject(dto, Address.class);
        log.debug("DTO convertido para entidade: {}", address);

        Address saved = addressRepository.save(address);
        log.info("Endereço salvo com sucesso, id={}", saved.getId());
        return ObjectMapper.parseObject(saved, AddressDTO.class);
    }

    public AddressDTO updateAddress(CreateAddressDTO dto, Long id) {
        log.info("Atualizando endereço ID={}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tentativa de atualizar endereço inexistente. ID={}", id);
                    return new ResourceNotFoundException("Endereço não encontrado com id: " + id);
                });

        log.debug("Dados recebidos para atualização: {}", dto);
        ObjectMapper.updateObject(dto, address);

        Address saved = addressRepository.save(address);

        log.info("Endereço ID={} atualizado com sucesso.", id);
        return ObjectMapper.parseObject(saved, AddressDTO.class);
    }

    public void deleteAddress(Long id) {
        log.debug("Solicitada exclusão do endereço ID={}", id);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Endereço não encontrado com id: " + id);
                    return new ResourceNotFoundException("Endereço não encontrado com id: " + id);
                });
        addressRepository.delete(address);

        log.info("Endereço ID={} deletado com sucesso.", id);
    }

}
