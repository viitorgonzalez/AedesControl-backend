package com.aedescontrol.backend.dto;

import com.aedescontrol.backend.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAddressDTO {

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String zipCode;

    @NotNull
    private Address.Status status;

    private Double latitude;
    private Double longitude;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Address.Status getStatus() {
        return status;
    }

    public void setStatus(Address.Status status) {
        this.status = status;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
