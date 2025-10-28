package com.property.campaign.service;

import com.property.campaign.model.Country;
import com.property.campaign.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CountryServiceImpl implements CountryService{

    @Autowired
    CountryRepository countryRepository;

    public Optional<Country> getCountryById(Integer CountryId){
        return countryRepository.findById(CountryId);
    }

    public Country findByCode(String code){
      return   countryRepository.findByCode(code);
    }

    public  List<Country> findByCodeIn(List<String> codes){
        return   countryRepository.findByCodeIn(codes);
    }

    public List<Country> findAll(){
        return   countryRepository.findAll();
    }



}
