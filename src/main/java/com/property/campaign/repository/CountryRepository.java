package com.property.campaign.repository;


import com.property.campaign.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country,Integer> {

    Country findByCode(String code);

    List<Country> findByCodeIn(List<String> codes);

}
