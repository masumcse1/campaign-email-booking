package com.property.campaign.repository;

import com.property.campaign.model.Property;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PropertySpecification {

    public static Specification<Property> getPropertyByAiCampaignTopic(Integer propertyId, Boolean urlForAiSystemsFound, Date urlForAiSystemsFoundLastUpdate) {
        return (Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (propertyId != null) {
                predicates.add(builder.equal(root.get("propertyId"), propertyId));
            }

            if (urlForAiSystemsFound != null) {
                predicates.add(builder.equal(root.get("urlForAiSystemsFound"), urlForAiSystemsFound));
            }

            predicates.add(builder.isNotNull(root.get("googleBusinessID")));

            if (urlForAiSystemsFoundLastUpdate != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("urlForAiSystemsFoundLastUpdate"), new Timestamp(urlForAiSystemsFoundLastUpdate.getTime())));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Property> getPropertyListByAiCampaignTopic(List<Integer> countryIds, Boolean urlForAiSystemsFound, Date urlForAiSystemsFoundLastUpdate) {
        return (Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (countryIds != null && !countryIds.isEmpty()) {
                predicates.add(root.get("country").get("id").in(countryIds));
            }

            if (urlForAiSystemsFound != null) {
                predicates.add(builder.equal(root.get("urlForAiSystemsFound"), urlForAiSystemsFound));
            }


            predicates.add(builder.isNotNull(root.get("googleBusinessID")));


            if (urlForAiSystemsFoundLastUpdate != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("urlForAiSystemsFoundLastUpdate"), new Timestamp(urlForAiSystemsFoundLastUpdate.getTime())));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }



}
