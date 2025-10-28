package com.property.campaign.service;

import com.property.campaign.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    Optional<Country> getCountryById(Integer CountryId);

    Country findByCode(String code);

    List<Country> findByCodeIn(List<String> codes);

    List<Country> findAll();

}
