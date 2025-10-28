package com.property.campaign.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country {

    @Id
    private Integer id;

    @Column(unique = true)
    private String name;

    private String fullName;

    private String code;

    private String codeA3;

    private Integer codeNumeric;

    public Country(Integer id) {
        this.id = id;
    }
}
